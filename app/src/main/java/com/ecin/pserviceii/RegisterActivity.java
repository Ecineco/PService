/*  Version-mysql clear  */
package com.ecin.pserviceii;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {
    String account, password, rePassword, userType;
    Button register;
    Connection conn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.Account);
                account = editText.getText().toString();
                editText = findViewById(R.id.textP);
                password = editText.getText().toString();
                editText = findViewById(R.id.retextP);
                rePassword = editText.getText().toString();
                Spinner spinner = findViewById(R.id.userType);
                userType = spinner.getSelectedItem().toString();

                boolean blocked = false;
                //Check account
                if (account.length() < 4 || account.length() > 15) {
                    blocked = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("账号长度应为4-15");
                    builder.setPositiveButton("确认", null);
                    builder.show();
                }
                //Check password
                if (!password.equals(rePassword) && !blocked) {
                    blocked = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("密码输入不一致");
                    builder.setPositiveButton("确认", null);
                    builder.show();
                }
                if ((password.length() < 6 || password.length() > 16) && !blocked) {
                    blocked = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("密码长度应为6-16");
                    builder.setPositiveButton("确认", null);
                    builder.show();
                }

                // Check if account exists
                if(!blocked) {
                    new Thread(new Runnable(){ // network operation
                        boolean blocked = false;
                        @Override
                        public void run(){
                            conn = DBHelper.getConnection();
                            PreparedStatement stmt;
                            try {
                                if (userType.equals("普通用户")) {
                                    stmt = conn.prepareStatement("SELECT * from UserInfo WHERE account=?");
                                } else {
                                    stmt = conn.prepareStatement("SELECT * from ProviderInfo WHERE account=?");
                                }
                                stmt.setString(1, account);
                                ResultSet result = stmt.executeQuery();
                                if(result.next()){
                                    blocked = true;
                                    dupHandler.sendEmptyMessage(1);
                                }
                                stmt.close();
                            } catch (SQLException e) {
                                // TODO
                                e.printStackTrace();
                            }

                            // Register
                            if (!blocked) {
                                PreparedStatement reStmt;
                                try {
                                    if (userType.equals("普通用户")) {
                                        int uCount = 0;
                                        PreparedStatement countStmt;
                                        countStmt = conn.prepareStatement("SELECT count(*) from UserInfo");
                                        ResultSet result = countStmt.executeQuery();
                                        if(result.next()){
                                            uCount = result.getInt(1);
                                        }
                                        reStmt = conn.prepareStatement("INSERT INTO UserInfo (UID, ACCOUNT, PASSWORD) VALUES (?, ?, ?)");
                                        reStmt.setInt(1, uCount + 1);
                                        reStmt.setString(2, account);
                                        reStmt.setString(3, password);
                                    } else {
                                        int pCount = 0;
                                        PreparedStatement countStmt;
                                        countStmt = conn.prepareStatement("SELECT count(*) from ProviderInfo");
                                        ResultSet result = countStmt.executeQuery();
                                        if(result.next()){
                                            pCount = result.getInt(1);
                                        }
                                        reStmt = conn.prepareStatement("INSERT INTO ProviderInfo (PID, ACCOUNT, PASSWORD) VALUES (?, ?, ?)");
                                        reStmt.setInt(1, pCount + 1);
                                        reStmt.setString(2, account);
                                        reStmt.setString(3, password);
                                    }
                                    reStmt.executeUpdate();
                                    reStmt.close();
                                    regHandler.sendEmptyMessage(1);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        });
    }

    Handler dupHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("账号已存在");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };

    Handler regHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("注册成功");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

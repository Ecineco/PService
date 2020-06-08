/*  Version-mysql clear  */
package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    String account, password, userType;
    Button login;
    Button register;
    Connection conn;
    int userId;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.button_toregister);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent("com.ecin.pserviceii.RegisterActivity"));
            }
        });
        login = findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.Account_L);
                account = editText.getText().toString();
                editText = findViewById(R.id.Password);
                password = editText.getText().toString();
                Spinner spinner = findViewById(R.id.userType_L);
                userType = spinner.getSelectedItem().toString();

                // Network operation
                new Thread(new Runnable(){
                @Override
                    public void run(){
                    conn = DBHelper.getConnection();
                    PreparedStatement stmt;
                    try{
                        if(userType.equals("普通用户")){
                            stmt = conn.prepareStatement("SELECT * FROM UserInfo WHERE account=?");
                        } else {
                            stmt = conn.prepareStatement("SELECT * FROM ProviderInfo WHERE account=?");
                        }
                        stmt.setString(1, account);
                        ResultSet result = stmt.executeQuery();
                        if(result.next()){
                            // Account exists, check if password corrects
                            String accountPassword = result.getString("password");
                            if(userType.equals("普通用户")) {
                                userId = result.getInt("uid");
                            }
                            else{
                                userId = result.getInt("pid");
                            }
                            if(password.equals(accountPassword)){ // login succeed
                                loginHandler.sendEmptyMessage(1);
                            } else { // password incorrect
                                wrongPassHandler.sendEmptyMessage(1);
                            }
                        } else { // Account not exist
                            emptyAccountHandler.sendEmptyMessage(1);
                        }
                    }catch(SQLException e){
                        // TODO
                        e.printStackTrace();
                    }
                }
                }).start();
            }
        });
    }

    Handler loginHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle extras = new Bundle();
            extras.putInt("UserId", userId);
            extras.putString("UserType", userType);
            Intent intent;
            if(userType.equals("普通用户")){
                intent = new Intent("com.ecin.pserviceii.ClientHomepageActivity");
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            }
            else{
                intent = new Intent("com.ecin.pserviceii.ProviderHomepageActivity");
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            }
        }
    };

    Handler wrongPassHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("提示");
            builder.setMessage("密码错误");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };

    Handler emptyAccountHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("提示");
            builder.setMessage("账号不存在");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

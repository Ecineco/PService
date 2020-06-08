package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProviderInfo4Activity extends AppCompatActivity {
    int userId; String searchKey;
    Button save;
    EditText editText;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerinfo4);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        searchKey = bundle.getString("searchKey");

        save = findViewById(R.id.button37);
        editText = findViewById(R.id.editText20);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT ? FROM ProviderInfo WHERE pid=?");
                    stmt.setString(1, searchKey);
                    stmt.setInt(2, userId);
                    ResultSet result = stmt.executeQuery();
                    if(result.next()){
                        editText.setText(result.getString(searchKey));
                    }
                } catch(SQLException ex){
                    //TODO
                }
            }
        }).start();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.getConnection();
                        PreparedStatement stmt;
                        try{
                            stmt = conn.prepareStatement("UPDATE ProviderInfo SET ?=? WHERE pid=?");
                            String updated = editText.getText().toString();
                            stmt.setString(1, searchKey);
                            stmt.setString(2, updated);
                            stmt.setInt(3, userId);
                            stmt.executeUpdate();
                            saveHandler.sendEmptyMessage(1);
                        } catch(SQLException ex){
                            //TODO
                            failHandler.sendEmptyMessage(1);
                        }
                    }
                }).start();
            }
        });
    }
    Handler saveHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            finish();
        }
    };
    Handler failHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(ProviderInfo4Activity.this);
            builder.setTitle("提示");
            builder.setMessage("修改失败，请重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

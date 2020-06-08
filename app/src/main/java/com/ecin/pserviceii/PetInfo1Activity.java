package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
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

public class PetInfo1Activity extends AppCompatActivity {
    int petId; String searchKey;
    EditText editText;
    Button save;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petinfo1);

        Bundle bundle = getIntent().getExtras();
        petId = bundle.getInt("petId");
        searchKey = bundle.getString("searchKey");

        editText = findViewById(R.id.editText7);
        save = findViewById(R.id.button13);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT ? FROM PetInfo WHERE tid=?");
                    stmt.setString(1, searchKey);
                    stmt.setInt(2, petId);
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
                        conn = DBHelper.getConnection();
                        PreparedStatement stmt;
                        try{
                            stmt = conn.prepareStatement("UPDATE PetInfo SET ?=? WHERE tid=?");
                            stmt.setString(1, searchKey);
                            stmt.setString(2, editText.getText().toString());
                            stmt.setInt(3, petId);
                            stmt.executeUpdate();
                            saveHandler.sendEmptyMessage(1);
                        } catch(SQLException ex){
                            //TODO
                            failHandler.sendEmptyMessage(1);
                        }
                    }
                }).start();
                finish();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(PetInfo1Activity.this);
            builder.setTitle("提示");
            builder.setMessage("修改失败，请重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

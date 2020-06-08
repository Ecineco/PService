package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceEditActivity extends AppCompatActivity {
    int userId, sid; String searchKey;
    Button infoEntry1, infoEntry2, infoEntry3, infoEntry4, infoEntry5, infoEntry6, deleteButton;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceedit);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        sid = bundle.getInt("serviceId");

        infoEntry1 = findViewById(R.id.info_entry8);
        infoEntry2 = findViewById(R.id.info_entry9);
        infoEntry3 = findViewById(R.id.info_entry10);
        infoEntry4 = findViewById(R.id.info_entry11);
        infoEntry5 = findViewById(R.id.info_entry12);
        infoEntry6 = findViewById(R.id.info_entry16);
        infoEntry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "name";
                Bundle extras = new Bundle();
                extras.putInt("serviceId", sid);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceInfo1Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "type";
                Bundle extras = new Bundle();
                extras.putInt("serviceId", sid);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceInfo2Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "start end";
                Bundle extras = new Bundle();
                extras.putInt("serviceId", sid);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceInfo3Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "fee period";
                Bundle extras = new Bundle();
                extras.putInt("serviceId", sid);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceInfo4Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "limit";
                Bundle extras = new Bundle();
                extras.putInt("serviceId", sid);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceInfo5Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "intro";
                Bundle extras = new Bundle();
                extras.putInt("serviceId", sid);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceInfo6Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        deleteButton = findViewById(R.id.button4);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        conn = DBHelper.getConnection();
                        PreparedStatement stmt;
                        try{
                            stmt = conn.prepareStatement("SELECT * FROM Preservation WHERE sid=? and state=?");
                            stmt.setInt(1, sid);
                            stmt.setString(2, "进行中");
                            ResultSet result = stmt.executeQuery();
                            if(result.next()){
                                predHandler.sendEmptyMessage(1);
                            }
                            else{
                                stmt = conn.prepareStatement("DELETE FROM Preservation WHERE sid=?");
                                stmt.setInt(1, sid);
                                stmt.executeUpdate();
                                stmt = conn.prepareStatement("DELETE FROM Provider_Service WHERE sid=?");
                                stmt.executeUpdate();
                                stmt = conn.prepareStatement("DELETE FROM Service WHERE sid=?");
                                stmt.executeUpdate();
                                finish();
                            }
                        } catch(SQLException ex){
                            //TODO
                            failHandler.sendEmptyMessage(1);
                        }
                    }
                }).start();
            }
        });
    }
    Handler predHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(ServiceEditActivity.this);
            builder.setTitle("提示");
            builder.setMessage("该项服务有正在进行的预约，无法删除");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
    Handler failHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(ServiceEditActivity.this);
            builder.setTitle("提示");
            builder.setMessage("修改失败，请重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

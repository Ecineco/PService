package com.ecin.pserviceii;

import android.app.AlertDialog;
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

public class ServiceInfo3Activity extends AppCompatActivity {
    int sid; String searchKey;
    Spinner start, end;
    Button save;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd3);

        Bundle bundle = getIntent().getExtras();
        sid = bundle.getInt("serviceId");
        searchKey = bundle.getString("searchKey");

        start = findViewById(R.id.spinner5);
        end = findViewById(R.id.spinner6);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT * FROM Service WHERE sid=?");
                    stmt.setInt(1, sid);
                    ResultSet result = stmt.executeQuery();
                    if(result.next()){
                        int curStart = result.getInt("start");
                        int curEnd = result.getInt("end");
                        for(int i = 1; i <= 24; i++){
                            if(curStart == i){
                                start.setSelection(i - 1, true);
                                break;
                            }
                        }
                        for(int i = 1; i <= 24; i++){
                            if(curEnd == i){
                                end.setSelection(i - 1, true);
                                break;
                            }
                        }
                    }
                } catch(SQLException ex){
                    //TODO
                }
            }
        }).start();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start.getSelectedItem().toString().equals("时") || end.getSelectedItem().toString().equals("时")){
                    missHandler.sendEmptyMessage(1);
                }
                else {
                    int currStart = 0, currEnd = 0;
                    String sText = start.getSelectedItem().toString();
                    String eText = end.getSelectedItem().toString();
                    int sEnd = 0, eEnd = 0;
                    for(int i = 0; i <= sText.length(); i++){
                        if(sText.charAt(i) == ':' || sText.charAt(i) == '：'){
                            sEnd = i;
                            break;
                        }
                    }
                    for(int i = 0; i <= eText.length(); i++){
                        if(eText.charAt(i) == ':' || eText.charAt(i) == '：'){
                            eEnd = i;
                            break;
                        }
                    }
                    currStart = Integer.parseInt(sText.substring(0,sEnd));
                    currEnd = Integer.parseInt(eText.substring(0,eEnd));
                    if(currStart >= currEnd){
                        wrongTimeHandler.sendEmptyMessage(1);
                    }
                    else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                conn = DBHelper.getConnection();
                                PreparedStatement stmt;
                                try {
                                    stmt = conn.prepareStatement("UPDATE Service SET ?=? WHERE sid=?");
                                    stmt.setString(1, "start");
                                    stmt.setInt(2, Integer.parseInt(start.getSelectedItem().toString()));
                                    stmt.setInt(3, sid);
                                    stmt.executeUpdate();
                                    stmt = conn.prepareStatement("UPDATE Service SET ?=? WHERE sid=?");
                                    stmt.setString(1, "end");
                                    stmt.setInt(2, Integer.parseInt(end.getSelectedItem().toString()));
                                    stmt.setInt(3, sid);
                                    stmt.executeUpdate();
                                    saveHandler.sendEmptyMessage(1);
                                } catch (SQLException ex) {
                                    //TODO
                                    failHandler.sendEmptyMessage(1);
                                }
                            }
                        }).start();
                        finish();
                    }
                }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(ServiceInfo3Activity.this);
            builder.setTitle("提示");
            builder.setMessage("修改失败，请重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
    Handler missHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(ServiceInfo3Activity.this);
            builder.setTitle("提示");
            builder.setMessage("请完整选择起始时间后重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
    Handler wrongTimeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(ServiceInfo3Activity.this);
            builder.setTitle("提示");
            builder.setMessage("请正确选择起始时间后重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

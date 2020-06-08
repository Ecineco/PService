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

public class ServiceInfo2Activity extends AppCompatActivity {
    int sid; String searchKey;
    Spinner serviceType;
    Button save;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd2);

        Bundle bundle = getIntent().getExtras();
        sid = bundle.getInt("serviceId");
        searchKey = bundle.getString("searchKey");

        serviceType = findViewById(R.id.spinner3);
        save = findViewById(R.id.button25);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT ? FROM Service WHERE sid=?");
                    stmt.setString(1, searchKey);
                    stmt.setInt(2, sid);
                    ResultSet result = stmt.executeQuery();
                    if(result.next()){
                        String currVal = result.getString(searchKey);
                        if(currVal.equals("宠物用品服务")){
                            serviceType.setSelection(0, true);
                        }
                        if(currVal.equals("宠物寄养服务")){
                            serviceType.setSelection(1, true);
                        }
                        if(currVal.equals("宠物医疗服务")){
                            serviceType.setSelection(2, true);
                        }
                        if(currVal.equals("宠物训练服务")){
                            serviceType.setSelection(0, true);
                        }
                        if(currVal.equals("宠物娱乐服务")){
                            serviceType.setSelection(0, true);
                        }
                        if(currVal.equals("其他宠物服务")){
                            serviceType.setSelection(0, true);
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        conn = DBHelper.getConnection();
                        PreparedStatement stmt;
                        try{
                            stmt = conn.prepareStatement("UPDATE Service SET ?=? WHERE sid=?");
                            stmt.setString(1, searchKey);
                            stmt.setString(2, serviceType.getSelectedItem().toString());
                            stmt.setInt(3, sid);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ServiceInfo2Activity.this);
            builder.setTitle("提示");
            builder.setMessage("修改失败，请重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

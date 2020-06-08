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

public class ServiceInfo4Activity extends AppCompatActivity {
    int sid; String searchKey;
    EditText editText;
    Spinner spinner;
    Button save;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd4);

        Bundle bundle = getIntent().getExtras();
        sid = bundle.getInt("serviceId");
        searchKey = bundle.getString("searchKey");

        editText = findViewById(R.id.editText11);
        spinner = findViewById(R.id.spinner7);
        save = findViewById(R.id.button35);
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
                        editText.setText(Float.toString(result.getFloat("fee")));
                        String currPeriod = result.getString("period");
                        if(currPeriod.equals("次")){
                            spinner.setSelection(0, true);
                        }
                        if(currPeriod.equals("时")){
                            spinner.setSelection(1, true);
                        }
                        if(currPeriod.equals("件")){
                            spinner.setSelection(2, true);
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
                            stmt.setString(1, "fee");
                            stmt.setFloat(2, Float.parseFloat(editText.getText().toString()));
                            stmt.setInt(3, sid);
                            stmt.executeUpdate();
                            stmt = conn.prepareStatement("UPDATE Service SET ?=? WHERE sid=?");
                            stmt.setString(1, "period");
                            stmt.setString(2, spinner.getSelectedItem().toString());
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ServiceInfo4Activity.this);
            builder.setTitle("提示");
            builder.setMessage("修改失败，请重试");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

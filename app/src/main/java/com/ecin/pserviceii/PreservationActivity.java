package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreservationActivity extends AppCompatActivity {
    int userId, serviceId, limit, sStart, sEnd, pid;
    String sName, pName, pAddress, pPhone;
    Float sFee;
    Button cer;
    TextView tv1, tv2, tv3, tv4;
    EditText et;
    int preTime;
    String startTime;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preservation);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        serviceId = bundle.getInt("serviceId");
        limit = bundle.getInt("limit");
        sName = bundle.getString("serviceName");
        pName = bundle.getString("providerName");
        pAddress = bundle.getString("providerAddress");
        pPhone = bundle.getString("providerPhone");
        sStart = bundle.getInt("preStart");
        sEnd = bundle.getInt("preEnd");
        sFee = bundle.getFloat("fee");
        pid = bundle.getInt("pid");

        tv1 = findViewById(R.id.text_apptitle4);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView5);
        tv4 = findViewById(R.id.textView25);
        et = findViewById(R.id.editText);
        tv1.setText(sName);
        tv2.setText(pName);
        tv3.setText("联系电话:" + pPhone);
        tv4.setText(Float.toString(sFee));
        et.setText(pAddress);

        ArrayList<String> time = new ArrayList<>();
        time.add("时");
        for(int i = sStart; i <= sEnd; i++){
            String toTime = "" + i + ":00";
            time.add(toTime);
        }

        Spinner timeSpinner = findViewById(R.id.spinner4);
        timeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time));
        String tmp = timeSpinner.getSelectedItem().toString();
        if(tmp.equals("时")){
            preTime = 0;
        }
        else{
            int endIndex = 2;
            for(int i = 2; i < tmp.length(); i++){
                if(tmp.charAt(i) == ':'){
                    endIndex = i;
                }
            }
            String num = tmp.substring(0, endIndex);
            preTime = Integer.parseInt(num);
        }
        cer = findViewById(R.id.button);
        cer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner month = findViewById(R.id.spinner);
                Spinner day = findViewById(R.id.spinner2);
                String selectedM = month.getSelectedItem().toString();
                String selectedD = day.getSelectedItem().toString();
                if(preTime == 0 || selectedD.equals("日") || selectedM.equals("月")){
                    timeMissHandler.sendEmptyMessage(1);
                }
                else{
                    startTime = "" + selectedM + "月" + selectedD + "日" + preTime + ":00";
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        conn = DBHelper.getConnection();
                        PreparedStatement stmt;
                        try{
                            stmt = conn.prepareStatement("SELECT COUNT(*) FROM Preservation WHERE sid=? and start=?");
                            stmt.setInt(1, serviceId);
                            stmt.setInt(2, preTime);
                            ResultSet result = stmt.executeQuery();
                            if(result.next()){
                                int currPre = result.getInt(1);
                                if(currPre >= limit){
                                    overPreHandler.sendEmptyMessage(1);
                                }
                                else{
                                    stmt = conn.prepareStatement("SELECT COUNT(*) FROM Preservation");
                                    ResultSet count = stmt.executeQuery();
                                    int preId = 0;
                                    if(count.next()){
                                        preId = count.getInt(1) + 1;
                                    }
                                    stmt = conn.prepareStatement("SELECT petid FROM UserInfo WHERE uid=?");
                                    stmt.setInt(1, userId);
                                    ResultSet pet = stmt.executeQuery();
                                    int petid = 0;
                                    if(pet.next()){
                                        petid = count.getInt("petid");
                                    }
                                    stmt = conn.prepareStatement("INSERT INTO Preservation (prid,sid,uid,pid,tid,start,expanse,state) VALUES(?,?,?,?,?,?,?,?)");
                                    stmt.setInt(1, preId);
                                    stmt.setInt(2, serviceId);
                                    stmt.setInt(3, userId);
                                    stmt.setInt(4, pid);
                                    stmt.setInt(5, petid);
                                    stmt.setString(6, startTime);
                                    stmt.setFloat(7, sFee);
                                    stmt.setString(8, "待商家确认");
                                    stmt.executeUpdate();
                                    succeedHandler.sendEmptyMessage(1);
                                }
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
    Handler timeMissHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(PreservationActivity.this);
            builder.setTitle("提示");
            builder.setMessage("未选择预约时间");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
    Handler overPreHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(PreservationActivity.this);
            builder.setTitle("提示");
            builder.setMessage("该时间段预约已满");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
    Handler succeedHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(PreservationActivity.this);
            builder.setTitle("提示");
            builder.setMessage("预约成功");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
    Handler failHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(PreservationActivity.this);
            builder.setTitle("提示");
            builder.setMessage("预约失败");
            builder.setPositiveButton("确认", null);
            builder.show();
        }
    };
}

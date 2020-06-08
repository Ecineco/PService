package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDetailActivity extends AppCompatActivity {
    int sid, userId, sLimit, sStart, sEnd, pid = 0;
    String sName, pName, pAddress, pPhone, sPeriod, sIntro;
    float sPrice;
    Button preservation;
    TextView tv1, tv2, tv3, tv4, tv5;
    EditText et1, et2;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedetail);

        Bundle bundle = getIntent().getExtras();
        sid = bundle.getInt("serviceId");
        userId = bundle.getInt("userId");

        tv1 = findViewById(R.id.text_apptitle9);
        tv2 = findViewById(R.id.textView30);
        et1 = findViewById(R.id.editText3);
        tv3 = findViewById(R.id.textView26);
        tv4 = findViewById(R.id.textView27);
        tv5 = findViewById(R.id.textView29);
        et2 = findViewById(R.id.editText4);

        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT * FROM Service WHERE sid=?");
                    stmt.setInt(1, sid);
                    ResultSet result = stmt.executeQuery();
                    if(result.next()){
                        sName = result.getString("name");
                        sPrice = result.getFloat("fee");
                        sStart = result.getInt("startTime");
                        sEnd = result.getInt("endTime");
                        sIntro = result.getString("intro");
                        sPeriod = result.getString("period");
                        sLimit = result.getInt("limit");
                    }
                    stmt = conn.prepareStatement("SELECT pid FROM Provider_Service WHERE sid=?");
                    stmt.setInt(1, sid);
                    result = stmt.executeQuery();
                    if(result.next()){
                        pid = result.getInt("pid");
                    }
                    stmt = conn.prepareStatement("SELECT * FROM Provider WHERE pid=?");
                    stmt.setInt(1, pid);
                    result = stmt.executeQuery();
                    if(result.next()){
                        pName = result.getString("name");
                        pAddress = result.getString("address");
                        pPhone = result.getString("phone");
                    }
                    infoHandler.sendEmptyMessage(1);
                } catch(SQLException ex){
                    //TODO
                }
            }
        }).start();

        preservation = findViewById(R.id.button_preservation);
        preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("serviceName", sName);
                extras.putString("providerName", pName);
                extras.putString("providerPhone", pPhone);
                extras.putString("providerAddress", pAddress);
                extras.putInt("preStart", sStart);
                extras.putInt("preEnd", sEnd);
                extras.putFloat("fee", sPrice);
                extras.putInt("serviceId", sid);
                extras.putInt("userId", userId);
                extras.putInt("limit", sLimit);
                extras.putInt("pid", pid);
                Intent intent = new Intent("com.ecin.pserviceii.PreservationActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    Handler infoHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            tv1.setText(sName);
            tv2.setText(pName);
            et1.setText("地址:" + pAddress);
            tv3.setText("联系电话:" + pPhone);
            tv4.setText(Float.toString(sPrice) + "/" + sPeriod);
            tv5.setText("" + sStart + ":00 —— " + sEnd + ":00");
            et2.setText(sIntro);
        }
    };
}

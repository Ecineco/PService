package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceAddActivity extends AppCompatActivity {
    Button infoEntry1, infoEntry2, infoEntry3, infoEntry4, infoEntry5, infoEntry6;
    Button add;
    int userId, serviceId = 1;
    String name = "", type = "", period = "", intro = "";
    int start = -1, end = -1, limit = -1;
    float fee = -1.0f;
    int request_Code1 = 1, request_Code2 = 2, request_Code3 = 3, request_Code4 = 4, request_Code5 = 5, request_Code6 = 6;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceadd);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");

        infoEntry1 = findViewById(R.id.info_entry17);
        infoEntry2 = findViewById(R.id.info_entry18);
        infoEntry3 = findViewById(R.id.info_entry19);
        infoEntry4 = findViewById(R.id.info_entry20);
        infoEntry5 = findViewById(R.id.info_entry21);
        infoEntry6 = findViewById(R.id.info_entry22);
        add = findViewById(R.id.button14);
        infoEntry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("com.ecin.pserviceii.ServiceInfoAdd1Activity"), request_Code1);
            }
        });
        infoEntry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("com.ecin.pserviceii.ServiceInfoAdd2Activity"), request_Code2);
            }
        });
        infoEntry3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("com.ecin.pserviceii.ServiceInfoAdd3Activity"), request_Code3);
            }
        });
        infoEntry4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("com.ecin.pserviceii.ServiceInfoAdd4Activity"), request_Code4);
            }
        });
        infoEntry5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("com.ecin.pserviceii.ServiceInfoAdd5Activity"), request_Code5);
            }
        });
        infoEntry6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("com.ecin.pserviceii.ServiceInfoAdd6Activity"), request_Code6);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.equals("") || type.equals("") || period.equals("") || start == -1 || end == -1 || limit == -1 || fee == -1.0f){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceAddActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("请把所有信息完善后重试");
                    builder.setPositiveButton("确认", null);
                    builder.show();
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            conn = DBHelper.getConnection();
                            PreparedStatement stmt;
                            try{
                                stmt = conn.prepareStatement("SELECT COUNT(*) FROM Service");
                                ResultSet resultSet = stmt.executeQuery();
                                if(resultSet.next()){
                                    serviceId = resultSet.getInt(1) + 1;
                                }
                                stmt = conn.prepareStatement("INSERT INTO Service(sid,name,type,start,end,period,fee,limit,intro) VALUES(?,?,?,?,?,?,?,?,?)");
                                stmt.setInt(1, serviceId);
                                stmt.setString(2, name);
                                stmt.setString(3, type);
                                stmt.setInt(4, start);
                                stmt.setInt(5, end);
                                stmt.setString(6, period);
                                stmt.setFloat(7, fee);
                                stmt.setInt(8, limit);
                                stmt.setString(9, intro);
                                stmt.executeUpdate();

                                stmt = conn.prepareStatement("SELECT COUNT(*) FROM Provider_Service");
                                ResultSet count = stmt.executeQuery();
                                int psid = 0;
                                if(count.next()){
                                    psid = count.getInt(1);
                                }
                                stmt = conn.prepareStatement("INSERT INTO Provider_Service(id, pid, sid) VALUES(?,?,?)");
                                stmt.setInt(1, psid);
                                stmt.setInt(2, userId);
                                stmt.setInt(3, serviceId);
                                stmt.executeUpdate();
                                finish();
                            } catch(SQLException ex){
                                //TODO
                            }
                        }
                    }).start();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == request_Code1){
            if(resultCode == RESULT_OK){
                name = data.getData().toString();
            }
        }
        if(requestCode == request_Code2){
            if(resultCode == RESULT_OK){
                type = data.getData().toString();
            }
        }
        if(requestCode == request_Code3){
            if(resultCode == RESULT_OK){
                String time = data.getData().toString();
                String[] times = time.split(" ");
                int endIndex = 0;
                for(int i = 0; i < times[0].length(); i++){
                    if(times[0].charAt(i) == ':' || times[0].charAt(i) == '：'){
                        endIndex = i;
                        break;
                    }
                }
                start = Integer.parseInt(times[0].substring(0, endIndex));
                for(int i = 0; i < times[1].length(); i++){
                    if(times[1].charAt(i) == ':' || times[0].charAt(i) == '：'){
                        endIndex = i;
                        break;
                    }
                }
                end = Integer.parseInt(times[1].substring(0, endIndex));
            }
        }
        if(requestCode == request_Code4){
            if(resultCode == RESULT_OK){
                String perTime = data.getData().toString();
                String[] values = perTime.split("/");
                fee = Float.parseFloat(values[0]);
                period = values[1];
            }
        }
        if(requestCode == request_Code5){
            if(resultCode == RESULT_OK){
                limit = Integer.parseInt(data.getData().toString());
            }
        }
        if(requestCode == request_Code6){
            if(resultCode == RESULT_OK){
                intro = data.getData().toString();
            }
        }
    }
}

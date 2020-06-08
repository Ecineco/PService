package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceEntryActivity extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> ids = new ArrayList<>();
    int userId, passSId;
    String serviceType;
    ListView listView;
    Connection conn;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceentry);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        serviceType = bundle.getString("serviceType");

        listView = findViewById(R.id.entrylist);
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT * FROM Service WHERE type=?");
                    stmt.setString(1, serviceType);
                    ResultSet result = stmt.executeQuery();
                    while(result.next()){
                        PreparedStatement stmt2;
                        String providerName = "";
                        String serviceName = result.getString("name");
                        stmt2 = conn.prepareStatement("SELECT pid FROM Provider_Service WHERE sid=?");
                        int sid = result.getInt("sid");
                        stmt2.setInt(1, sid);
                        ResultSet resultSet = stmt2.executeQuery();
                        if(result.next()){
                            int pid = resultSet.getInt("pid");
                            stmt2 = conn.prepareStatement("SELECT name FROM Provider WHERE pid=?");
                            stmt2.setInt(1, pid);
                            ResultSet pName = stmt2.executeQuery();
                            if(pName.next()){
                                providerName = pName.getString("name");
                            }
                        }
                        ids.add(new Integer(sid));
                        String show = serviceName + "\n" + providerName;
                        names.add(show);
                    }
                    loadHandler.sendEmptyMessage(1);
                } catch(SQLException ex){
                    //TODO
                }
            }
        }).start();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passSId = ids.get(position);

                Bundle extras = new Bundle();
                extras.putInt("serviceId", passSId);
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceDetailActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    Handler loadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };
}

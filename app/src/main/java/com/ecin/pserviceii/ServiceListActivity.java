package com.ecin.pserviceii;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceListActivity extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> ids = new ArrayList<>();
    int userId, passSId;
    ListView listView;
    Connection conn;
    ArrayAdapter adapter;
    ImageButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicelist);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");

        listView = findViewById(R.id.service_lv);
        add = findViewById(R.id.imageButton9);
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT sid FROM Provider_Service WHERE pid=?");
                    stmt.setInt(1, userId);
                    ResultSet result = stmt.executeQuery();
                    while(result.next()){
                        PreparedStatement stmt2;
                        int sid = result.getInt("sid");
                        stmt2 = conn.prepareStatement("SELECT * FROM Service WHERE sid=?");
                        stmt2.setInt(1, sid);
                        ResultSet resultSet = stmt2.executeQuery();
                        String serviceName = resultSet.getString("name");
                        ids.add(new Integer(sid));
                        names.add(serviceName);
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
                Intent intent = new Intent("com.ecin.pserviceii.ServiceEditActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.pserviceii.ServiceAddActivity");
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

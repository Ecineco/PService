package com.ecin.pserviceii;

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

public class PreListActivity extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> ids = new ArrayList<>();
    int userId, passPrId;
    ListView listView;
    Connection conn;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelist);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        listView = findViewById(R.id.prelist_c);
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("SELECT * FROM Preservation WHERE uid=?");
                    stmt.setInt(1, userId);
                    ResultSet result = stmt.executeQuery();
                    while(result.next()){
                        PreparedStatement stmt2;
                        String serviceName = "";
                        int prid = result.getInt("prid");
                        int sid = result.getInt("sid");
                        stmt2 = conn.prepareStatement("SELECT name FROM Service WHERE sid=?");
                        stmt2.setInt(1, sid);
                        ResultSet resultSet = stmt2.executeQuery();
                        if(result.next()){
                            serviceName = resultSet.getString("name");
                        }
                        ids.add(new Integer(prid));
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
                passPrId = ids.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle extras = new Bundle();
                        extras.putInt("preservationId", passPrId);
                        Intent intent = new Intent("com.ecin.pserviceii.PreDetailClientActivity");
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                }).start();
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

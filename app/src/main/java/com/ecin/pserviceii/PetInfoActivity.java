package com.ecin.pserviceii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetInfoActivity extends AppCompatActivity {
    int userId;
    Button entry1, entry2;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petinfo);

        final Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");

        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = DBHelper.getConnection();
                PreparedStatement stmt;
                try{
                    stmt = conn.prepareStatement("Update UserInfo SET petid=?");
                    stmt.setInt(1, userId);
                    stmt.executeUpdate();
                    stmt = conn.prepareStatement("SELECT * FROM PetInfo WHERE tid=?");
                    stmt.setInt(1, userId);
                    ResultSet result = stmt.executeQuery();
                    if(result.next()){

                    }
                    else {
                        stmt = conn.prepareStatement("INSERT INTO PetInfo (tid) VALUES(?)");
                        stmt.setInt(1, userId);
                        stmt.executeUpdate();
                    }
                } catch(SQLException ex){
                    //TODO
                }

            }
        }).start();

        entry1 = findViewById(R.id.info_entry6);
        entry2 = findViewById(R.id.info_entry7);
        entry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("petId", userId);
                extras.putString("searchKey", "species");
                Intent intent = new Intent("com.ecin.pserviceii.PetInfo1Activity");
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
        entry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("petId", userId);
                extras.putString("searchKey", "intro");
                Intent intent = new Intent("com.ecin.pserviceii.PetInfo2Activity");
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
    }
}

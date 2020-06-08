package com.ecin.pserviceii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfoActivity extends AppCompatActivity {
    int userId; String searchKey;
    Button infoEntry1, infoEntry2, infoEntry3, infoEntry4, infoEntry5;
    Button petEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientinfo);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        infoEntry1 = findViewById(R.id.info_entry1);
        infoEntry2 = findViewById(R.id.info_entry2);
        infoEntry3 = findViewById(R.id.info_entry3);
        infoEntry4 = findViewById(R.id.info_entry4);
        infoEntry5 = findViewById(R.id.info_entry5);
        petEntry = findViewById(R.id.button2);
        infoEntry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "name";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ClientInfo1Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "sex";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ClientInfo2Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "phone";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ClientInfo3Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "address";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ClientInfo4Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        infoEntry5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "email";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                Intent intent = new Intent("com.ecin.pserviceii.ClientInfo5Activity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        petEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.ecin.pserviceii.PetInfoActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
}

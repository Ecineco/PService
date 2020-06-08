package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClientHomepageActivity extends AppCompatActivity {
    int userId; String userType;
    String serviceType;
    Button userInfoEntry, preListEntry;
    View entry1, entry2, entry3, entry4, entry5, entry6;
    TextView text1, text2, text3, text4, text5, text6;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_clienthomepage);

            AlertDialog.Builder builder = new AlertDialog.Builder(ClientHomepageActivity.this);
            builder.setTitle("提示");
            builder.setMessage("登录成功");
            builder.setPositiveButton("确认", null);
            builder.show();

            Bundle bundle = getIntent().getExtras();
            userId = bundle.getInt("userId");
            userType = bundle.getString("userType");


            userInfoEntry = findViewById(R.id.button_myinfo);
            userInfoEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    Intent intent = new Intent("com.ecin.pserviceii.UserInfoActivity");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
            preListEntry = findViewById(R.id.button_mypre);
            preListEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    Intent intent = new Intent("com.ecin.pserviceii.PreListActivity");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
            entry1 = findViewById(R.id.view);
            entry1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物用品服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            text1 = findViewById(R.id.type_entry1);
            text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物用品服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            entry2 = findViewById(R.id.view2);
            entry2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物寄养服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            text2 = findViewById(R.id.type_entry2);
            text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物寄养服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            entry3 = findViewById(R.id.view3);
            entry3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    serviceType = "宠物医疗服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            text3 = findViewById(R.id.type_entry3);
            text3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物医疗服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            entry4 = findViewById(R.id.view4);
            entry4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物训练服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            text4 = findViewById(R.id.type_entry4);
            text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物训练服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            entry5 = findViewById(R.id.view5);
            entry5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物娱乐服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            text5 = findViewById(R.id.type_entry5);
            text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "宠物娱乐服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            entry6 = findViewById(R.id.view6);
            entry6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "其他宠物服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
            text6 = findViewById(R.id.type_entry6);
            text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceType = "其他宠物服务";
                    Bundle extras = new Bundle();
                    extras.putInt("userId", userId);
                    extras.putString("serviceType", serviceType);
                    Intent intent = new Intent("com.ecin.pserviceii.ServiceEntryActivity");
                    startActivity(intent);
                }
            });
        }
}

package com.ecin.pserviceii;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProviderHomepageActivity extends AppCompatActivity {
    int userId; String userType;
    View view7, view8;
    TextView entry1, entry2;
    Button providerInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerhomepage);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        userType = bundle.getString("userType");

        AlertDialog.Builder builder = new AlertDialog.Builder(ProviderHomepageActivity.this);
        builder.setTitle("提示");
        builder.setMessage("登录成功");
        builder.setPositiveButton("确认", null);
        builder.show();

        view7 = findViewById(R.id.view7);
        view8 = findViewById(R.id.view8);
        entry1 = findViewById(R.id.provider_entry1);
        entry2 = findViewById(R.id.provider_entry2);
        providerInfo = findViewById(R.id.provider_info);

        view7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceListActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.ecin.pserviceii.PreListProActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        entry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.ecin.pserviceii.ServiceListActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        entry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.ecin.pserviceii.PreListActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        providerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                Intent intent = new Intent("com.ecin.pserviceii.ProviderInfoActivity");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
}

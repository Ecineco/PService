package com.ecin.pserviceii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProviderInfoActivity extends AppCompatActivity {
    int userId; String searchKey;
    Button infoEntry1, infoEntry2, infoEntry3, infoEntry4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerinfo);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");

        infoEntry1 = findViewById(R.id.info_entry);
        infoEntry2 = findViewById(R.id.info_entry14);
        infoEntry3 = findViewById(R.id.info_entry15);
        infoEntry4 = findViewById(R.id.info_entry13);
        infoEntry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "name";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                startActivity(new Intent("com.ecin.pserviceii.ProviderInfo1Activity"));
            }
        });
        infoEntry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "phone";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                startActivity(new Intent("com.ecin.pserviceii.ProviderInfo2Activity"));
            }
        });
        infoEntry3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "address";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                startActivity(new Intent("com.ecin.pserviceii.ProviderInfo3Activity"));
            }
        });
        infoEntry4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = "intro";
                Bundle extras = new Bundle();
                extras.putInt("userId", userId);
                extras.putString("searchKey", searchKey);
                startActivity(new Intent("com.ecin.pserviceii.ProviderInfo4Activity"));
            }
        });
    }
}

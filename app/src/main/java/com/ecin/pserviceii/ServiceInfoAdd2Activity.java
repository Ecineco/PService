package com.ecin.pserviceii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceInfoAdd2Activity extends AppCompatActivity {
    Spinner serviceType;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd2);

        serviceType = findViewById(R.id.spinner3);
        save = findViewById(R.id.button25);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.setData(Uri.parse(serviceType.getSelectedItem().toString()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}

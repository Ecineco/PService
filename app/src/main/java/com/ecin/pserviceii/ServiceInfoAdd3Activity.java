package com.ecin.pserviceii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceInfoAdd3Activity extends AppCompatActivity {
    Spinner start, end;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd3);

        start = findViewById(R.id.spinner5);
        end = findViewById(R.id.spinner6);
        save = findViewById(R.id.button24);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = start.getSelectedItem().toString() + " " + end.getSelectedItem();
                Intent data = new Intent();
                data.setData(Uri.parse(time));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}

package com.ecin.pserviceii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceInfoAdd4Activity extends AppCompatActivity {
    EditText fee;
    Spinner period;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd4);

        fee = findViewById(R.id.editText11);
        period = findViewById(R.id.spinner7);
        save = findViewById(R.id.button26);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = fee.getText().toString() + "/" + period.getSelectedItem().toString();
                Intent data = new Intent();
                data.setData(Uri.parse(value));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}

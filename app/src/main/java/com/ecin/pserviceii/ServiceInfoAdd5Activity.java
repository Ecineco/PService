package com.ecin.pserviceii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceInfoAdd5Activity extends AppCompatActivity {
    EditText limit;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd5);

        limit = findViewById(R.id.editText10);
        save = findViewById(R.id.button27);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.setData(Uri.parse(limit.getText().toString()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}

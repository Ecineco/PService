package com.ecin.pserviceii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceInfoAdd6Activity extends AppCompatActivity {
    EditText intro;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceinfoadd6);

        intro = findViewById(R.id.editText);
        save = findViewById(R.id.button28);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.setData(Uri.parse(intro.getText().toString()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}

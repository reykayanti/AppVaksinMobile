package com.example.py7.appbiodata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnPesertaVaksin =(Button)findViewById(R.id.btnPesertaVaksin);

        btnPesertaVaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(StartActivity.this, MainActivity.class);
                startActivity(a);
            }
        });

    }
}
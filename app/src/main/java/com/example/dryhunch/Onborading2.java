package com.example.dryhunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dryhunch.R;

public class Onborading2 extends AppCompatActivity {

    Button forward11;
    Button forward21;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onborading2);

        forward11 = findViewById(R.id.forward11);
        forward11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onborading2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        forward21 = findViewById(R.id.forward21);
        forward21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onborading2.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
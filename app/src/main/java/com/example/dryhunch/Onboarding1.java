package com.example.dryhunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dryhunch.R;
public class Onboarding1 extends AppCompatActivity {

    Button forward1;
    Button forward2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding1);
        forward1 = findViewById(R.id.forward1);
        forward1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(Onboarding1.this,Onborading2.class);
                 startActivity(intent);
            }
        });

        forward2 = findViewById(R.id.forward2);
        forward2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding1.this,Onborading2.class);
                startActivity(intent);
            }
        });

    }
}
package com.example.dryhunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import dryhunch.R;

public class advice_page extends AppCompatActivity {
public String acc;
public TextView advice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_page);
        Intent intent = getIntent();
        acc = intent.getStringExtra("accuracy");
        int accuracy = Integer.parseInt(acc);

        advice = findViewById(R.id.advice);

        if(accuracy<=50)
        {
            advice.setText("It is unlikely for the drought to take place in coming year. So you can buy seeds and fertilizers and start farming");
        }
        else if(accuracy>50 && accuracy<=65)
        {
            advice.setText("Please avoid taking huge amount money as loan to buy agricultural products sunce there is a chance that drought can take place this month");
        }
        else if(accuracy>65 && accuracy<85)
        {
            advice.setText("Chances of drought are high in upcoming weeks. Hence please save water for drought condition. Here are some ways to do so " +"https://youtu.be/0R1vR-MOixY");
            advice.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
        {
            advice.setText("Very High probability of drought taking place. Please avoid planting seeds or carrying out agricultural activities. Please do not take any loans for now and observe the situation for 2 weeks");
        }



    }
}
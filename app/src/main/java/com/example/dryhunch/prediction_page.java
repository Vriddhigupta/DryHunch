package com.example.dryhunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import dryhunch.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class prediction_page extends AppCompatActivity {

    public String city;
    public TextView pred_text;
    public ImageView mapimage;
    public Button advice_btn;
    public String accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_page);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");

        pred_text=findViewById(R.id.pred_text);
        mapimage=findViewById(R.id.mapimage);
        advice_btn=findViewById(R.id.advice_btn);

        pred_request req = new pred_request();
        req.setPlace(city);

        Call<pred_response> loginResponseCall = predApi.getService().get_pred(req);
        loginResponseCall.enqueue(new Callback<pred_response>() {
            @Override
            public void onResponse(Call<pred_response> call, Response<pred_response> response) {
                if (response.isSuccessful()) {
                    pred_response predResponse = response.body();
                    accuracy= predResponse.getAccuracy();
                    pred_text.setText(predResponse.getAccuracy()+"%");
                    Picasso.get().load(predResponse.getLink()).into(mapimage);

                } else {
                    String message = "Unable to get prediction. An error occured";
                    Toast.makeText(prediction_page.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<pred_response> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(prediction_page.this, message, Toast.LENGTH_LONG).show();
            }
        });

        advice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(prediction_page.this,advice_page.class);
                intent.putExtra("accuracy", accuracy);
                startActivity(intent);
            }
        });

    }
}
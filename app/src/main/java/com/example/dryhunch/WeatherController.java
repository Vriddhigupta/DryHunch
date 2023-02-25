package com.example.dryhunch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import dryhunch.R;

public class WeatherController extends AppCompatActivity {
    final int REQUEST_CODE = 123;

    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "13c59103faf71e0bdf7e65752bc51ee5";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    // TODO: Set LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;


    // Member Variables:
    TextView mCityLabel, conditionLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;

    // TODO: Declare a LocationManager and a LocationListener here:
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Linking the elements in the layout to Java code
        mCityLabel = (TextView) findViewById(R.id.locationTV);

        mWeatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
        conditionLabel = (TextView) findViewById(R.id.condition);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);

    }


    // TODO: Add onResume() here:

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("c", "onResume: Getting Location of user");
        getWeatherForCurrentLocation();


    }


    private void getWeatherForCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO locationListenerGPS onStatusChanged
            }
            @Override
            public void onLocationChanged(Location myLocation) {
                Double currentLatitude = myLocation.getLatitude();
                Double currentLongitude = myLocation.getLongitude();
                RequestParams requestParams = new RequestParams();
                requestParams.put("lat",currentLatitude);
                requestParams.put("lon", currentLongitude);
                requestParams.put("appid", APP_ID);

                Log.d("Cima", "latitude: " + currentLatitude);
                Log.d("Clima", "longitude: " + currentLongitude);
                networking(requestParams);

            }

        };

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more detail.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        Double currentLatitude = myLocation.getLatitude();
        Double currentLongitude = myLocation.getLongitude();
        RequestParams requestParams = new RequestParams();
        requestParams.put("lat",currentLatitude);
        requestParams.put("lon", currentLongitude);
        requestParams.put("appid", APP_ID);

        Log.d("Cima", "latitude: " + currentLatitude);
        Log.d("Clima", "longitude: " + currentLongitude);
        networking(requestParams);

        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    private void networking(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                Log.d("Clima", "onSuccess: " + response.toString());
                WeatherDataModel weatherDataModel = WeatherDataModel.fromJson(response);

                mCityLabel.setText(weatherDataModel.getmCity());
                conditionLabel.setText(weatherDataModel.condition);
                mTemperatureLabel.setText(weatherDataModel.getmTemperature());
                int resID = getResources().getIdentifier(weatherDataModel.getmIconName(), "drawable", getPackageName());
                mWeatherImage.setImageResource(resID);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Clima", "onFailure: " + e.toString());
                Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("clima", "onRequestPermissionsResult: permission granted");
                getWeatherForCurrentLocation();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null)
            locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.example.dryhunch;

import java.net.CookieManager;
import java.net.CookiePolicy;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class predApi {


        public static Retrofit getRetrofit()
        {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            CookieManager cookieHandler = new CookieManager();
            cookieHandler.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(httpLoggingInterceptor)
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            // OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            Retrofit retrofit= new Retrofit.Builder()
                    .baseUrl("https://dryhunch.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit;
//        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://rrr-server.onrender.com/")
//                .client(okHttpClient)
//                .build();
//
//        return retrofit;
        }

        public static UserService getService()
        {
            UserService userService = getRetrofit().create(UserService.class);
            return userService;
        }

}

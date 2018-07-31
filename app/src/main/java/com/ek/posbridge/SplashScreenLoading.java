package com.ek.posbridge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ek.posbridge.UI.LoginActivity;


public class SplashScreenLoading extends  AppCompatActivity{
    private Handler mHandler = new Handler();
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( SplashScreenLoading.this, LoginActivity.class );
                startActivity( intent );
            }
        }, 2500);



    }

 }



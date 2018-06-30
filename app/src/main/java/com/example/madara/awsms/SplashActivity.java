package com.example.madara.awsms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Space;

import com.example.madara.awsms.utils.Session;

public class SplashActivity extends AppCompatActivity {
    private static int time_out = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final boolean i = Session.getInstance().isUserLoggedIn();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i) {
                    Intent goToHome = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(goToHome);
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this,AuthenticationActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, time_out);
    }

}

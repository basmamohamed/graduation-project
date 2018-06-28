package com.example.madara.awsms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

    }
    public void goToSignUp (View view){
        Intent startNewActivity1 = new Intent(this , SignupActivity.class);
        startActivity(startNewActivity1);
    }

    public void goToLogIn (View view){
        Intent startNewActivity3 = new Intent(this , LoginActivity.class);
        startActivity(startNewActivity3);
    }
}

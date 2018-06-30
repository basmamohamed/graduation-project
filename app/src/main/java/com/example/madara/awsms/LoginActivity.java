package com.example.madara.awsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.madara.awsms.models.LoginRequest;
import com.example.madara.awsms.models.LoginResponse;
import com.example.madara.awsms.models.User;
import com.example.madara.awsms.utils.Session;
import com.example.madara.awsms.webservices.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText Email_Text, Password_Text;
    String PASSWORD, EMAIL;
    ProgressBar progressBar;
    private Call<LoginResponse> mLoginCall;
    private LoginResponse loginResponse = new LoginResponse();
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email_Text = (EditText) findViewById(R.id.email);
        Password_Text = (EditText) findViewById(R.id.password);

    }

    public void LogIn(View view) {
        EMAIL = Email_Text.getText().toString();
        PASSWORD = Password_Text.getText().toString();
        if (TextUtils.isEmpty(EMAIL)) {
            Email_Text.setError("Please enter your username");
            Email_Text.requestFocus();
            return;
        } else if (TextUtils.isEmpty(PASSWORD)) {
            Password_Text.setError("Please enter your password");
            Password_Text.requestFocus();
            return;
        } else {
            loginUser(EMAIL, PASSWORD);
        }
    }


        private void loginUser(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = email;
        loginRequest.password = password;
        loginRequest.remember = "true";
        mLoginCall = WebService.getInstance().getApi().loginUser(loginRequest);
        mLoginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //loginResponse = response.body();
                try {
                    if (!mLoginCall.isCanceled()) {
                        if (response.body().success == "true") {

                            user_id = response.body().message;
                            Toast.makeText(LoginActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                            User user = new User();
                            String cookie = response.headers().get("Set-Cookie");
                            cookie = cookie.substring(0,cookie.indexOf(";"));

                            user.user_id = user_id;
                            user.user_cookie = cookie;
                            Session.getInstance().startSession(user);
                            Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
                            goToHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(goToHome);
                            finish();
                            progressDialog.cancel();

                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(LoginActivity.this, response.body().result.get(0).description, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if(!mLoginCall.isCanceled()) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoginCall != null) {
            mLoginCall.cancel();
        }
    }
}






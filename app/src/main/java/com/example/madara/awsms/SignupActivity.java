package com.example.madara.awsms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.madara.awsms.models.LoginRequest;
import com.example.madara.awsms.models.LoginResponse;
import com.example.madara.awsms.models.RegisterRequest;
import com.example.madara.awsms.models.RegisterResponse;
import com.example.madara.awsms.models.User;
import com.example.madara.awsms.webservices.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    EditText name, email, password, confirm_password ;
    String Name, Email, Password , GenderText , Confirm_Password;
    RadioGroup radioGroupGender;
    ProgressBar progressBar;
    Button btnsignup;
    private Call<RegisterResponse> mRegisterCall;
    private RegisterResponse registerResponse = new RegisterResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);
        name = (EditText) findViewById(R.id.user_name);
        email = (EditText) findViewById(R.id.user_email);
        password = (EditText) findViewById(R.id.user_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);

    }

    public void signup(View view) {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }
        else {
            Name = name.getText().toString();
            Email = email.getText().toString();
            Password = password.getText().toString();
            Confirm_Password = confirm_password.getText().toString();
            GenderText = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
            registerUser(Name,Email,Password,Confirm_Password,GenderText);

        }}


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        btnsignup.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;


        if (email.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (name.getText().toString().isEmpty() || name.getText().toString().length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }
            String pass = password.getText().toString();
        if (pass.isEmpty()||pass.length()<6) {
            password.setError("at least 6 characters with digits, upper and lower case letters and special character");
            valid = false;
        } else {
            password.setError(null);
        }
        if (!confirm_password.getText().toString().equals(password.getText().toString()) ) {
            confirm_password.setError("password is not matched");
            valid = false;
        } else {
            confirm_password.setError(null);
        }

        return valid;
    }

    private void registerUser(String name,String email, String password,String confirm_password,String gender) {
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = email;
        registerRequest.password = password;
        registerRequest.name = name ;
        registerRequest.confirm_password = confirm_password;
        registerRequest.gender = gender;
        mRegisterCall = WebService.getInstance().getApi().registerUser(registerRequest);
        mRegisterCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                try {
                    if (!mRegisterCall.isCanceled()) {
                        if (response.body().success == "true") {
                            Toast.makeText(SignupActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                            Intent goToHome = new Intent(SignupActivity.this, HomeActivity.class);
                            goToHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(goToHome);
                            finish();
                            progressDialog.cancel();

                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(SignupActivity.this, response.body().result.get(0).description, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(SignupActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                if(!mRegisterCall.isCanceled()) {
                    progressDialog.cancel();
                    Toast.makeText(SignupActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRegisterCall != null) {
            mRegisterCall.cancel();
        }
    }
}










package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madara on 6/27/18.
 */

public class LoginRequest {
   // {email: "qwer@dom.com", password: "AdminDef@1t", remember: true}
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("remember")
    public String remember;
}

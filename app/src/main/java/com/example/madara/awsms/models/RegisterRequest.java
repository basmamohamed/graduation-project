package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madara on 6/27/18.
 */

public class RegisterRequest {
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("confirm_password")
    public String confirm_password;
    @SerializedName("gender")
    public String gender;
}

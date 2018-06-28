package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by madara on 6/27/18.
 */

public class LoginResponse {
    //{"success":true,"message":"10"}
    //{"success":false,"result":[{"code":"Mismatch","description":"User doesn't exist or password mismatch"}]}
    @SerializedName("success")
    public String success;
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public List<Result> result;
    public static class Result{
        @SerializedName("code")
        public String code;
        @SerializedName("description")
        public String description;
    }

}

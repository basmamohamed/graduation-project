package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by madara on 6/27/18.
 */

public class RegisterResponse {
    @SerializedName("success")
    public String success;
    @SerializedName("result")
    public List<LoginResponse.Result> result;
    public static class Result{
        @SerializedName("code")
        public String code;
        @SerializedName("description")
        public String description;
    }
}

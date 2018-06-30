package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersCheckResponse {
    @SerializedName("success")
    public String success;
    @SerializedName("result")
    public List<Result> result;
    public static class Result{
        @SerializedName("item1")
        public String item1;
        @SerializedName("item2")
        public String item2;
    }
    @SerializedName("message")
    public String message;
}

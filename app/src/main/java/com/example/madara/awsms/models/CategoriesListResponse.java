package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesListResponse {
    @SerializedName("success")
    public String success;
    @SerializedName("result")
    public List<Result> result;
    public class Result{
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
    }
}

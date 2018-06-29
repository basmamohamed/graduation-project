package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

public class GetAccountResponse {
    @SerializedName("success")
    public String success;
    @SerializedName("result")
    public Result result;
    public class  Result {
        @SerializedName("id")
        public String id;
        @SerializedName("email")
        public String email;
        @SerializedName("gender")
        public String gender;
        @SerializedName("name")
        public String name;
    }
}

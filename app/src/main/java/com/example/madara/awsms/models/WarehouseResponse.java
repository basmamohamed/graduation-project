package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WarehouseResponse {
    @SerializedName("success")
    public String success;
    @SerializedName("result")
    public List<WarehouseResponse.Result> result;
    public static class Result{
        @SerializedName("priceSchema")
        public PriceSchema priceSchema;
        public class PriceSchema {
            @SerializedName("baseCost")
            public String baseCost;
            @SerializedName("dailyRate")
            public String dailyRate;
            @SerializedName("taxPercent")
            public String taxPercent;
        }
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public String id;
        @SerializedName("available")
        public String available;
        }
}


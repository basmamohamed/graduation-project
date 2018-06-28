package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class  OrdersCheckRequest {
    public List<request> requests;
    public class request{
    @SerializedName("warehouseId")
    public String warehouseId;
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
    @SerializedName("quantity")
    public String quantity;
    @SerializedName("startDate")
    public String startDate;
    @SerializedName("endDate")
    public String endDate;
    }

}


package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by madara on 7/2/18.
 */

public class OrdersListResponse {
    @SerializedName("success")
    public String success;
    @SerializedName("result")
    public List<Result> result;
    public class Result{
        @SerializedName("id")
        public String id;
        @SerializedName("price")
        public String price;
        @SerializedName("placementDate")
        public String placementDate;
        @SerializedName("storageSpaces")
        public List<OrdersListResponse.StorageSpaces> storageSpaces;

    }
    public class StorageSpaces{
        @SerializedName("id")
        public String id;
        @SerializedName("startDate")
        public String startDate;
        @SerializedName("endDate")
        public String endDate;
        @SerializedName("item")
        public List<OrdersListResponse.Item> items;
        @SerializedName("name")
        public String name;
    }
    public class Item{
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("arriveDate")
        public String arriveDate;
        @SerializedName("removeDate")
        public String removeDate;
    }

}

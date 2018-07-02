package com.example.madara.awsms.models;

import android.widget.LinearLayout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by madara on 7/2/18.
 */

public class OrdersListRequest {
    @SerializedName("success")
    public String success;
    public class Result{
        @SerializedName("id")
        public String id;
        @SerializedName("price")
        public String price;
        @SerializedName("placementDate")
        public String placementDate;
        @SerializedName("storageSpaces")
        public List<OrdersListRequest.StorageSpaces> storageSpaces;

    }
    public class StorageSpaces{
        @SerializedName("id")
        public String id;
        @SerializedName("startDate")
        public String startDate;
        @SerializedName("endDate")
        public String endDate;
        @SerializedName("item")
        public List<OrdersListRequest.item> items;
        @SerializedName("name")
        public String name;
    }
    public class item{
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

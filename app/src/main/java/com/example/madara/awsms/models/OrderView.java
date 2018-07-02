package com.example.madara.awsms.models;

/**
 * Created by madara on 7/2/18.
 */

public class OrderView {
    public String id;
    public String price;
    public String date;
    public String numOfStorageSpaces;

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getNumOfStorageSpaces() {
        return numOfStorageSpaces;
    }
}

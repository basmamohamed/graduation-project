package com.example.madara.awsms.models;

public class Warehouses {
    String mBaseCost;
    String mDailyRate;
    String mTaxPercent;
    String mName;
    String mAvailable;
    String mId;
    public Warehouses (String baseCost ,String dailyRate,String taxPercent , String name , String id , String available){
        this.mBaseCost = baseCost;
        this.mDailyRate = dailyRate;
        this.mTaxPercent = taxPercent;
        this.mName = name;
        this.mAvailable = available;
        this.mId = id;

    }

    public String getmBaseCost() {
        return mBaseCost;
    }

    public String getmDailyRate() {
        return mDailyRate;
    }

    public String getmTaxPercent() {
        return mTaxPercent;
    }

    public String getmName() {
        return mName;
    }

    public String getmAvailable() {
        return mAvailable;
    }

    public String getmId() {
        return mId;
    }
}

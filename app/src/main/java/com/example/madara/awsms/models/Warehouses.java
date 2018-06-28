package com.example.madara.awsms.models;

public class Warehouses {
    String mBaseCost;
    String mDailyRate;
    String mTaxPercent;
    String mName;
    String mAvailable;
    public Warehouses (String baseCost ,String dailyRate,String taxPercent , String name ,  String available){
        mBaseCost = baseCost;
        mDailyRate = dailyRate;
        mTaxPercent = taxPercent;
        mName = name;
        mAvailable = available;

    }
    public String getBaseCost() {
        return mBaseCost;
    }
    public void setBaseCost(String baseCost) {mBaseCost = baseCost;}
    public String getDailyRate() {
        return mDailyRate;
    }
    public void setDailyRate(String dailyRate) {
        mDailyRate = dailyRate;
    }
    public String getTaxPercent() {
        return mTaxPercent;
    }
    public void setTaxPercent(String taxPercent) {mTaxPercent = taxPercent;}
    public String getName() {
        return mName;
    }
    public void setName(String name) {mName = name;}
    public String getAvailable() {
        return mAvailable;
    }
    public void setAvailable(String available) {mAvailable = available;}
}

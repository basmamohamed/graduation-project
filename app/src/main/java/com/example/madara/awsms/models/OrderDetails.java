package com.example.madara.awsms.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EGYTEK on 01/05/2018.
 */

public class OrderDetails implements Parcelable{
    private String mUnits;
    private String mOrderDate;
    private String mDeliverDate;

    public OrderDetails(String units , String orderDate , String deliverDate){
        mUnits=units;
        mOrderDate=orderDate;
        mDeliverDate=deliverDate;
    }
    public String getUnits() {
        return mUnits;
    }
    public void setUnits(String units) {mUnits = units;}
    public String getOrderDate() {
        return mOrderDate;
    }
    public void setOrderDate(String orderDate) {
        mOrderDate = orderDate;
    }
    public String getDeliverDate() {
        return mDeliverDate;
    }
    public void setDeliverDate(String deliverDate) {mDeliverDate = deliverDate;}

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.mUnits);
        dest.writeString(this.mOrderDate);
        dest.writeString(this.mDeliverDate);
    }
    public static final Creator<OrderDetails> CREATOR
            = new Creator<OrderDetails>() {
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };
    private OrderDetails(Parcel in) {
        this.mUnits = in.readString();
        this.mOrderDate = in.readString();
        this.mDeliverDate = in.readString();

    }

}

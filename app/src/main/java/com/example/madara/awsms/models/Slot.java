package com.example.madara.awsms.models;

import java.util.Date;

/**
 * Created by madara on 7/2/18.
 */

public class Slot {
    public Date startDate;
    public Date endDate;
    public OrdersListResponse.Item item;
    public String storageId;
    public String orderId;
    public boolean getFree(){
        return item!=null;
    }
}

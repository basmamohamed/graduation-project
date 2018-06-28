package com.example.madara.awsms.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by madara on 2/22/18.
 */

public class User extends RealmObject{
    @SerializedName("userid")
    public String user_id;
    @SerializedName("cookie")
    public String user_cookie;

}

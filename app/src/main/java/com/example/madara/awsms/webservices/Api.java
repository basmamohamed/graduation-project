package com.example.madara.awsms.webservices;





import com.example.madara.awsms.WarehouseList;
import com.example.madara.awsms.models.LoginRequest;
import com.example.madara.awsms.models.LoginResponse;
import com.example.madara.awsms.models.OrdersCheckRequest;
import com.example.madara.awsms.models.OrdersCheckResponse;
import com.example.madara.awsms.models.RegisterRequest;
import com.example.madara.awsms.models.RegisterResponse;
import com.example.madara.awsms.models.User;
import com.example.madara.awsms.models.WarehouseRequest;
import com.example.madara.awsms.models.WarehouseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by madara on 2/22/18.
 */

public interface Api {
    @Headers("content-type: application/json")
    @POST("Account/Login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    @POST("Account/Register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);
    @POST("Warehouse/List")
    //@POST("api-test.php")  // Elsayed test Api
    Call<WarehouseResponse> warehouseList(@Body WarehouseRequest warehouseRequest);
    @POST("register")
    Call<OrdersCheckResponse> OrderCheck(@Body OrdersCheckRequest ordersCheckRequest , @Header("Authorization") String authHeader );
//    @POST("bindcard")

////
////    //@POST("register-user.php")
//    @POST("register")
//    Call<MainResponse> registerUser(@header("cookie"),@Body User user);
//    @POST("bindcard")
//    Call<MainResponse> bindCard(@Body Card card);
//    @FormUrlEncoded
//    @POST("getMyCards")
//    Call<List<CardResponse>> getCards(@Field("user_id") int user_id);
//    @POST("unbindcard")
//    Call<MainResponse> unbindcard(@Body Card card);
//      @FormUrlEncoded
//    @POST("userProfileData")
//    Call<UserProfileResponse> getUserProfile(@Field("userid") int user_id);
//
//    @POST("getGarages")
//    Call<List<Garage>> getGarages(@Body GarageRequest garageRequest);
//    @POST("getUserGarages")
//    Call<List<Garage>> getUserGarages(@Body GarageRequest garageRequest);
////    //edit user information
//    @POST("ChangeUsername")
//    Call<MainResponse> changeName(@Body User user);
//    @POST("ChangeEmail")
//    Call<MainResponse> changeEmail(@Body User user);
//    @POST("ChangePassword")
//    Call<MainResponse> changePassword(@Body User user);
//    @POST("ChangePhoneNumber")
//    Call<MainResponse> changePhone(@Body User user);
//    @POST("charge")
//    Call<MainResponse> charge(@Body ChargeRequest chargeRequest);
//    @POST("feedback")
//    Call<MainResponse> feedback(@Body SendFeedbackRequest sendFeedbackRequest);
//    @POST("reserveSlot")
//    Call<MainResponse> reserveGarage(@Body ReserveRequest reserveRequest);
//    @POST("searchForGarage")
//    Call<List<Garage>> searchGarages(@Body SearchRequest searchRequest);
}

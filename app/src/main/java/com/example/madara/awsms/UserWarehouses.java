package com.example.madara.awsms;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.madara.awsms.adapters.UserWarehouseAdapter;
import com.example.madara.awsms.models.CategoriesListResponse;
import com.example.madara.awsms.models.OrderView;
import com.example.madara.awsms.models.OrdersListResponse;
import com.example.madara.awsms.models.Slot;
import com.example.madara.awsms.utils.Session;
import com.example.madara.awsms.webservices.WebService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserWarehouses extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Call<CategoriesListResponse> mCategoriesListCall;
    private Call<OrdersListResponse> mOrdersListCall;
    private List<Slot> slots = new ArrayList<Slot>();
    private List<OrderView> orderViews = new ArrayList<OrderView>();
    private UserWarehouseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_warehouses);
        recyclerView = findViewById(R.id.user_warehouses_recyclerview);
        swipeRefreshLayout = findViewById(R.id.user_warehouses_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserWarehouses.this));
        getOrders();

    }

    private void getCategories() {
        final ProgressDialog progressDialog = new ProgressDialog(UserWarehouses.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Categories...");
        progressDialog.show();
        String cookie = Session.getInstance().getUser().user_cookie;
        cookie = cookie;
        mCategoriesListCall = WebService.getInstance().getApi().getCategoriesList(cookie);
        mCategoriesListCall.enqueue(new Callback<CategoriesListResponse>() {
            @Override
            public void onResponse(Call<CategoriesListResponse> call, Response<CategoriesListResponse> response) {
                try {
                    if (!mCategoriesListCall.isCanceled()) {
                        if (response.body().success == "true") {
                            Toast.makeText(UserWarehouses.this, "success", Toast.LENGTH_SHORT).show();


                            progressDialog.cancel();
                            mCategoriesListCall = null;


                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(UserWarehouses.this, "failed", Toast.LENGTH_SHORT).show();
                            mCategoriesListCall = null;

                        }
                    }
                } catch (Exception e) {
                    if (!mCategoriesListCall.isCanceled()) {
                        progressDialog.cancel();
                        Toast.makeText(UserWarehouses.this, "failed", Toast.LENGTH_SHORT).show();
                        mCategoriesListCall = null;
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoriesListResponse> call, Throwable t) {
                if (!mCategoriesListCall.isCanceled()) {
                    Toast.makeText(UserWarehouses.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    mCategoriesListCall = null;
                }
            }
        });
    }

    private void getOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(UserWarehouses.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Orders...");
        progressDialog.show();
        String cookie = Session.getInstance().getUser().user_cookie;
        cookie = cookie;
        Log.e("TAG", "here1");
        mOrdersListCall = WebService.getInstance().getApi().getUserOrdersList(cookie);
        mOrdersListCall.enqueue(new Callback<OrdersListResponse>() {
            @Override
            public void onResponse(Call<OrdersListResponse> call, Response<OrdersListResponse> response) {
                try {
                    if (!mOrdersListCall.isCanceled()) {
                        if (response.body().success == "true") {
                            Log.e("TAG", "here");
                            Toast.makeText(UserWarehouses.this, "success", Toast.LENGTH_SHORT).show();
                            for (OrdersListResponse.Result result : response.body().result) {
                                OrderView orderView = new OrderView();
                                orderView.id = result.id;
                                orderView.price = result.price;
                                orderView.date = result.placementDate;
                                orderView.numOfStorageSpaces = String.valueOf(result.storageSpaces.size());
                                orderViews.add(orderView);
                            }
                            adapter = new UserWarehouseAdapter(orderViews, UserWarehouses.this);
                            recyclerView.setAdapter(adapter);
                            progressDialog.cancel();
                            mOrdersListCall = null;

                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(UserWarehouses.this, "failed", Toast.LENGTH_SHORT).show();
                            mOrdersListCall = null;

                        }
                    }
                } catch (Exception e) {
                    if (!mOrdersListCall.isCanceled()) {
                        progressDialog.cancel();
                        Toast.makeText(UserWarehouses.this, "failed", Toast.LENGTH_SHORT).show();
                        mOrdersListCall = null;
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersListResponse> call, Throwable t) {
                if (!mOrdersListCall.isCanceled()) {
                    progressDialog.cancel();
                    Log.e("TAG", t.toString());
                    Toast.makeText(UserWarehouses.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    mOrdersListCall = null;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCategoriesListCall != null) {
            mCategoriesListCall.cancel();
        } else if (mOrdersListCall != null) {
            mOrdersListCall.cancel();
        }
    }
}

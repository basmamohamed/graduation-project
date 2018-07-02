package com.example.madara.awsms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madara.awsms.adapters.WarehouseAdapter;
import com.example.madara.awsms.models.OrderDetails;
import com.example.madara.awsms.models.WarehouseRequest;
import com.example.madara.awsms.models.WarehouseResponse;
import com.example.madara.awsms.models.Warehouses;
import com.example.madara.awsms.webservices.WebService;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    public static final String TAG = "OrderActivity";

    private Call<WarehouseResponse> mWarehouseCall;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static ArrayList<Warehouses> warehouseItem = new ArrayList<Warehouses>();
    private WarehouseAdapter warehouseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = findViewById(R.id.warehouses_recyclerview);
        swipeRefreshLayout = findViewById(R.id.warehouses_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                if (mWarehouseCall == null) {
                    warehouseList();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if (mWarehouseCall == null) {
            warehouseList();
        }


    }


    private void warehouseList() {
        final ProgressDialog progressDialog = new ProgressDialog(OrderActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final WarehouseRequest warehouseRequest = new WarehouseRequest();
        mWarehouseCall = WebService.getInstance().getApi().warehouseList(warehouseRequest);
        mWarehouseCall.enqueue(new Callback<WarehouseResponse>() {
            @Override
            public void onResponse(Call<WarehouseResponse> call, Response<WarehouseResponse> response) {
                try {
                    if (!mWarehouseCall.isCanceled()) {
                        if (response.body().success == "true") {
                            Toast.makeText(OrderActivity.this, "success", Toast.LENGTH_SHORT).show();
                            for(int i=0;i<response.body().result.size();i++){
                                String basecost = response.body().result.get(i).priceSchema.baseCost;
                                String dailyRate = response.body().result.get(i).priceSchema.dailyRate;
                                String taxPercent = response.body().result.get(i).priceSchema.taxPercent;
                                String name = response.body().result.get(i).name;
                                String id = response.body().result.get(i).id;
                                String available = response.body().result.get(i).available;
                                warehouseItem.add(new Warehouses(basecost,dailyRate,taxPercent,name,id,available));
                            }
                            warehouseAdapter = new WarehouseAdapter(warehouseItem,OrderActivity.this);
                            recyclerView.setAdapter(warehouseAdapter);
                            progressDialog.cancel();
                            mWarehouseCall = null;


                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(OrderActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            mWarehouseCall = null;

                        }
                    }
                } catch (Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(OrderActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    mWarehouseCall = null;

                }
            }

            @Override
            public void onFailure(Call<WarehouseResponse> call, Throwable t) {
                if (!mWarehouseCall.isCanceled()) {
                    progressDialog.cancel();
//                    warehouseItem.add(new Warehouses("10","10","10","warehouse test","10","10"));
//                    warehouseItem.add(new Warehouses("10","10","10","warehouse test2","10","10"));
//                    warehouseItem.add(new Warehouses("10","10","10","warehouse test3","10","10"));
//                    warehouseItem.add(new Warehouses("10","10","10","warehouse test4","10","10"));
//                    warehouseAdapter = new WarehouseAdapter(warehouseItem,OrderActivity.this);
//                    recyclerView.setAdapter(warehouseAdapter);
                    Toast.makeText(OrderActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    mWarehouseCall = null;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWarehouseCall != null) {
            mWarehouseCall.cancel();
        }

    }


}



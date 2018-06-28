package com.example.madara.awsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.madara.awsms.adapters.WarehouseAdapter;
import com.example.madara.awsms.models.LoginRequest;
import com.example.madara.awsms.models.LoginResponse;
import com.example.madara.awsms.models.OrderDetails;
import com.example.madara.awsms.models.User;
import com.example.madara.awsms.models.WarehouseRequest;
import com.example.madara.awsms.models.WarehouseResponse;
import com.example.madara.awsms.models.Warehouses;
import com.example.madara.awsms.utils.Session;
import com.example.madara.awsms.webservices.WebService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseList extends AppCompatActivity {
    private Call<WarehouseResponse> mWarehouseCall;
    private WarehouseResponse warehouseResponse = new WarehouseResponse();
    Spinner spinner;
    public static ArrayList<Warehouses> warehouseItem =new ArrayList<Warehouses>() ;
    WarehouseAdapter warehouseAdapter;
    Warehouses warehouses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_list);
        spinner = (Spinner)findViewById(R.id.warehouseList);
        warehouseList();

    }
    private void warehouseList() {
        final ProgressDialog progressDialog = new ProgressDialog(WarehouseList.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        WarehouseRequest warehouseRequest = new WarehouseRequest();
        mWarehouseCall = WebService.getInstance().getApi().warehouseList(warehouseRequest);
        mWarehouseCall.enqueue(new Callback<WarehouseResponse>() {
            @Override
            public void onResponse(Call<WarehouseResponse> call, Response<WarehouseResponse> response) {
                try {
                    if (!mWarehouseCall.isCanceled()) {
                        if (response.body().success == "true") {
                            Toast.makeText(WarehouseList.this,"success",Toast.LENGTH_SHORT).show();
                            String baseCost = response.body().result.get(0).priceSchema.baseCost;
                            String dailyRate = response.body().result.get(0).priceSchema.dailyRate;
                            String taxPercent = response.body().result.get(0).priceSchema.taxPercent;
                            String name = response.body().result.get(0).name;
                            String available = response.body().result.get(0).available;
                            Warehouses warehouses = new Warehouses(baseCost , dailyRate , taxPercent , name , available);
                            warehouseItem.add(warehouses);
                            WarehouseAdapter warehouseAdapter = new WarehouseAdapter(WarehouseList.this,warehouseItem);
// Specify the layout to use when the list of choices appears
                            warehouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                            spinner.setAdapter(warehouseAdapter);
                            progressDialog.cancel();

                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(WarehouseList.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(WarehouseList.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WarehouseResponse> call, Throwable t) {
                if(!mWarehouseCall.isCanceled()) {
                    progressDialog.cancel();
                    Toast.makeText(WarehouseList.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
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

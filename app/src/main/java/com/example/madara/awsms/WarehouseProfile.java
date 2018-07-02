package com.example.madara.awsms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.madara.awsms.models.OrderConfirmResponse;
import com.example.madara.awsms.models.OrdersCheckRequest;
import com.example.madara.awsms.models.OrdersCheckResponse;
import com.example.madara.awsms.utils.Session;
import com.example.madara.awsms.webservices.WebService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseProfile extends AppCompatActivity {
    EditText Units ,OrderDate, DeliverDate;
    private Call<OrdersCheckResponse> mCheckCall;
    private Call<OrderConfirmResponse> mConfirmCall;
    private String warehouse_name,warehouse_id,available,warehouseDailyrate,warehouseBasecost,warehouseTaxpercent;
    private Button warehouseReserve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_profile);
        final Calendar calendar=Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Units = (EditText) findViewById(R.id.number_of_unit_edit);
        OrderDate = (EditText) findViewById(R.id.order_date_edit);
        DeliverDate = (EditText) findViewById(R.id.Deliver_date_edit);
        warehouseReserve = (Button) findViewById(R.id.warehouse_reserve);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent()!=null){
             warehouse_name = getIntent().getStringExtra("warehouseName");
             warehouse_id = getIntent().getStringExtra("warehouse_id");
             available = getIntent().getStringExtra("warehouseAvailable");
             warehouseDailyrate = getIntent().getStringExtra("warehouseDailyrate");
             warehouseBasecost = getIntent().getStringExtra("warehouseBasecost");
            warehouseTaxpercent = getIntent().getStringExtra("warehouseTaxpercent");
            getSupportActionBar().setTitle(warehouse_name);
        }
        OrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DatePickerDialog Orderdate = new DatePickerDialog(WarehouseProfile.this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        OrderDate.setText(year +"-"+month +"-"+dayOfMonth);
                    }
                },year,month,day);
                Orderdate.setTitle("select order date");
                Orderdate.show();
            }
        });
        DeliverDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog Deliverdate = new DatePickerDialog(WarehouseProfile.this,AlertDialog.THEME_HOLO_DARK,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        DeliverDate.setText(year +"-"+month +"-" +dayOfMonth);
                    }
                },year,month,day);
                Deliverdate.setTitle("select deliver date");
                Deliverdate.show();
            }
        });
        warehouseReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }
    public void cancel(View view) {
        Log.e("Tag",OrderDate.getText().toString());
        Units.getText().clear();
        OrderDate.getText().clear();
        DeliverDate.getText().clear();
    }

    private void submit(){
        if(Units.getText().toString().isEmpty()){
            Units.setError("Enter quantity");
        }
        else if(OrderDate.getText().toString().isEmpty()){
            OrderDate.setError("Enter order date");
        }
        else if(DeliverDate.getText().toString().isEmpty()){
            DeliverDate.setError("Enter deliver date");
        }
        else {
            final ProgressDialog progressDialog = new ProgressDialog(WarehouseProfile.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Checking...");
            progressDialog.show();
            String quantity = Units.getText().toString();
            String startDate = OrderDate.getText().toString();
            String endDate = DeliverDate.getText().toString();
            List<OrdersCheckRequest> ordersCheckRequest = new ArrayList<OrdersCheckRequest>();
            OrdersCheckRequest order = new OrdersCheckRequest();
            OrdersCheckRequest.PriceSchema priceSchema = order.new PriceSchema();
            order.warehouseId = warehouse_id;
            order.startDate = startDate;
            order.endDate = endDate;
            order.quantity = quantity;
            priceSchema.baseCost = warehouseBasecost;
            priceSchema.dailyRate = warehouseDailyrate;
            priceSchema.taxPercent = warehouseTaxpercent;
            order.priceSchema = priceSchema;
            ordersCheckRequest.add(order);
            //String cookie = Session.getInstance().getUser().user_cookie;
            String cookie = "cookie";
            mCheckCall = WebService.getInstance().getApi().orderCheck(ordersCheckRequest,cookie);
            mCheckCall.enqueue(new Callback<OrdersCheckResponse>() {
                @Override
                public void onResponse(Call<OrdersCheckResponse> call, Response<OrdersCheckResponse> response) {
                    try {
                        if (!mCheckCall.isCanceled()) {
                            if (response.body().success == "true") {
                                Toast.makeText(WarehouseProfile.this,response.body().message,Toast.LENGTH_SHORT).show();
                                Log.e("TAG","here");
                                progressDialog.cancel();
                                confirm(response.body().message);
                            } else if (response.body().success == "false") {
                                progressDialog.cancel();
                                Toast.makeText(WarehouseProfile.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(WarehouseProfile.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<OrdersCheckResponse> call, Throwable t) {
                    if(!mCheckCall.isCanceled()) {
                        progressDialog.cancel();
                        Toast.makeText(WarehouseProfile.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
    private void confirm(String orderId){
        final ProgressDialog progressDialog = new ProgressDialog(WarehouseProfile.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Confirming...");
        progressDialog.show();
        //String cookie = Session.getInstance().getUser().user_cookie;
        String cookie = "cookie";
        mConfirmCall = WebService.getInstance().getApi().orderConfirm(orderId,cookie);
        mConfirmCall.enqueue(new Callback<OrderConfirmResponse>() {
            @Override
            public void onResponse(Call<OrderConfirmResponse> call, Response<OrderConfirmResponse> response) {
                try {
                    if (!mConfirmCall.isCanceled()) {
                        if (response.body().success == "true") {
                            Toast.makeText(WarehouseProfile.this,"Success",Toast.LENGTH_SHORT).show();
                            Log.e("TAG","here");
                            progressDialog.cancel();

                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(WarehouseProfile.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(WarehouseProfile.this, "failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderConfirmResponse> call, Throwable t) {
                if(!mConfirmCall.isCanceled()) {
                    progressDialog.cancel();
                    Toast.makeText(WarehouseProfile.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

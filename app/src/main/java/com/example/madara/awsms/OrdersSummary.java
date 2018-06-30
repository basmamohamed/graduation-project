package com.example.madara.awsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.madara.awsms.adapters.OrderAdapter;
import com.example.madara.awsms.adapters.WarehouseAdapter;
import com.example.madara.awsms.models.LoginRequest;
import com.example.madara.awsms.models.LoginResponse;
import com.example.madara.awsms.models.OrderDetails;
import com.example.madara.awsms.models.OrdersCheckRequest;
import com.example.madara.awsms.models.OrdersCheckResponse;
import com.example.madara.awsms.models.User;
import com.example.madara.awsms.models.WarehouseRequest;
import com.example.madara.awsms.models.WarehouseResponse;
import com.example.madara.awsms.models.Warehouses;
import com.example.madara.awsms.utils.Session;
import com.example.madara.awsms.webservices.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.madara.awsms.OrderActivity.Item;
import static com.example.madara.awsms.OrderActivity.warehouseItem;

public class OrdersSummary extends AppCompatActivity {
    private OrderDetails order;
    String baseCost;
    String dailyRate;
    String taxPercent;
    String quantity;
    String startDate;
    String endDate;
    String warehouseId;
    private LinearLayout list;
    int i;
    private Call<OrdersCheckResponse> mOrderCheck;
    private OrdersCheckResponse loginResponse = new OrdersCheckResponse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_summary);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            order = bundle.getParcelable("order");
            item();
            baseCost = order.getWarehouses().getBaseCost();
            dailyRate = order.getWarehouses().getDailyRate();
            taxPercent = order.getWarehouses().getTaxPercent();
            quantity = order.getUnits();
            startDate = order.getDeliverDate();
            endDate = order.getOrderDate();
            warehouseId = order.getWarehouses().getId();
        }
    }
        public void item() {
            OrderAdapter OrderAdapter = new OrderAdapter(this, Item);
            ListView OrderList = (ListView) findViewById(R.id.summaryListView);
            OrderList.setAdapter(OrderAdapter);
        }
        public void check (View view){
        for (i=0;i<Item.size();i++) {
            orderCheck(warehouseId, baseCost, dailyRate, taxPercent, quantity, startDate, endDate);
        }
        }

    private void orderCheck(String warehouseId,String baseCost, String dailyRate,String taxPercent,String quantity,String startDate,String endDate) {
        final ProgressDialog progressDialog = new ProgressDialog(OrdersSummary.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        OrdersCheckRequest ordersCheckRequest = new OrdersCheckRequest();
            ordersCheckRequest.requests.get(i).warehouseId = warehouseId;
            ordersCheckRequest.requests.get(i).priceSchema.baseCost = baseCost;
            ordersCheckRequest.requests.get(i).priceSchema.dailyRate = dailyRate;
            ordersCheckRequest.requests.get(i).priceSchema.taxPercent = taxPercent;
            ordersCheckRequest.requests.get(i).quantity = quantity;
            ordersCheckRequest.requests.get(i).startDate = startDate;
            ordersCheckRequest.requests.get(i).endDate = endDate;
        String cookie = Session.getInstance().getUser().user_cookie;
        mOrderCheck = WebService.getInstance().getApi().orderCheck(ordersCheckRequest,cookie);
        mOrderCheck.enqueue(new Callback<OrdersCheckResponse>() {
            @Override
            public void onResponse(Call<OrdersCheckResponse> call, Response<OrdersCheckResponse> response) {
                try {
                    if (!mOrderCheck.isCanceled()) {
                        if (response.body().success == "true") {
                            Toast.makeText(OrdersSummary.this,response.body().message,Toast.LENGTH_SHORT).show();
                            for (int j = 0 ; j < response.body().result.size(); j++){
                                if (response.body().result.get(j).item1 != null){
                                    list = (LinearLayout)findViewById(R.id.Container);
                                    list.setBackgroundColor(0x8BC34A);
                                }
                                else {
                                    list = (LinearLayout)findViewById(R.id.Container);
                                    list.setBackgroundColor(0xFF5252);
                                }
                            }
                            progressDialog.cancel();

                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(OrdersSummary.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(OrdersSummary.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersCheckResponse> call, Throwable t) {
                if(!mOrderCheck.isCanceled()) {
                    progressDialog.cancel();
                    Toast.makeText(OrdersSummary.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrderCheck != null) {
            mOrderCheck.cancel();
        }
    }
}

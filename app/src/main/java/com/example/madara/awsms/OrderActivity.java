package com.example.madara.awsms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static ArrayList<OrderDetails> Item = new ArrayList<OrderDetails>();
    OrderDetails order;
    TextView UnitsText, OrderDateText, DeliverDateText;
    String units, orderDate, deliverDate;
    EditText  Units ,OrderDate, DeliverDate;
    private Call<WarehouseResponse> mWarehouseCall;
    private WarehouseResponse warehouseResponse = new WarehouseResponse();
    Spinner spinner;
    Warehouses clickedItem;
    public static ArrayList<Warehouses> warehouseItem =new ArrayList<Warehouses>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        final Calendar calendar=Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Units = (EditText) findViewById(R.id.number_of_unit_edit);
        OrderDate = (EditText) findViewById(R.id.order_date_edit);
        DeliverDate = (EditText) findViewById(R.id.Deliver_date_edit);
        UnitsText = (TextView) findViewById(R.id.numberOfUnitsList);
        OrderDateText = (TextView) findViewById(R.id.orderDateList);
        DeliverDateText = (TextView) findViewById(R.id.deliverDateList);
        spinner = (Spinner)findViewById(R.id.warehouseList);

        OrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DatePickerDialog Orderdate = new DatePickerDialog(OrderActivity.this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        OrderDate.setText(year +"/" + month +"/" + dayOfMonth);
                    }
                },year,month,day);
                Orderdate.setTitle("select order date");
                Orderdate.show();
            }
        });
        DeliverDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog Deliverdate = new DatePickerDialog(OrderActivity.this,AlertDialog.THEME_HOLO_DARK,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        DeliverDate.setText(year +"/" + month +"/" + dayOfMonth);
                    }
                },year,month,day);
                Deliverdate.setTitle("select deliver date");
                Deliverdate.show();
            }
        });
    }


    public void plus(View view) {
        units = Units.getText().toString();
        orderDate = OrderDate.getText().toString();
        deliverDate = DeliverDate.getText().toString();
        Warehouses warehouseList = clickedItem;
        order = new OrderDetails(units, orderDate, deliverDate,warehouseList);
        Item.add(order);
        Units.getText().clear();
        OrderDate.getText().clear();
        DeliverDate.getText().clear();
    }

    public void cancel(View view) {
        Item.clear();
        Intent goToAntherActivity = new Intent(this, HomeActivity.class);
        startActivity(goToAntherActivity);
        finish();
    }

    public void submit (View view){
        units = Units.getText().toString();
        orderDate = OrderDate.getText().toString();
        deliverDate = DeliverDate.getText().toString();
        Warehouses warehouseList = clickedItem;
        order = new OrderDetails(units, orderDate, deliverDate,warehouseList);
        Item.add(order);
        Intent intent=new Intent(this,OrdersSummary.class);
        Bundle b = new Bundle();
        b.putParcelable("order", order);
        intent.putExtras(b);
        Units.getText().clear();
        OrderDate.getText().clear();
        DeliverDate.getText().clear();
        startActivity(intent);
    }
    private void warehouseList() {
        final ProgressDialog progressDialog = new ProgressDialog(OrderActivity.this);
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
                            Toast.makeText(OrderActivity.this,"success",Toast.LENGTH_SHORT).show();
                            warehouseItem.add(new Warehouses("","","","",""));
                            for (int i = 0 ;i<response.body().result.size();i++){
                                String baseCost = response.body().result.get(i).priceSchema.baseCost;
                                String dailyRate = response.body().result.get(i).priceSchema.dailyRate;
                                String taxPercent = response.body().result.get(i).priceSchema.taxPercent;
                                String name = response.body().result.get(i).name;
                                String available = response.body().result.get(i).available;
                                Warehouses warehouses = new Warehouses(baseCost , dailyRate , taxPercent , name , available);
                                warehouseItem.add(warehouses);}
                            WarehouseAdapter warehouseAdapter = new WarehouseAdapter(OrderActivity.this,warehouseItem);
                            warehouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(warehouseAdapter);



                            progressDialog.cancel();

                        } else if (response.body().success == "false") {
                            progressDialog.cancel();
                            Toast.makeText(OrderActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    progressDialog.cancel();
                    Toast.makeText(OrderActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WarehouseResponse> call, Throwable t) {
                if(!mWarehouseCall.isCanceled()) {
                    progressDialog.cancel();
                    Toast.makeText(OrderActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position!=0){
            clickedItem = (Warehouses) adapterView.getItemAtPosition(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



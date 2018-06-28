package com.example.madara.awsms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.madara.awsms.models.OrderDetails;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {
    public static ArrayList<OrderDetails> Item = new ArrayList<OrderDetails>();
    OrderDetails order;
    TextView UnitsText, OrderDateText, DeliverDateText;
    String units, orderDate, deliverDate;
    EditText  Units ,OrderDate, DeliverDate;

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
        order = new OrderDetails(units, orderDate, deliverDate);
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
        order = new OrderDetails(units, orderDate, deliverDate);
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


}



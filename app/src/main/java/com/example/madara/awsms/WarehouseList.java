package com.example.madara.awsms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_list);



    }

}

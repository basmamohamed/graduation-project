package com.example.madara.awsms.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.madara.awsms.R;
import com.example.madara.awsms.WarehouseProfile;
import com.example.madara.awsms.models.Warehouses;
import com.example.madara.awsms.utils.Session;
import com.example.madara.awsms.webservices.WebService;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.GarageHolder>{
    private static final String TAG = "GarageAdapter";
    private List<Warehouses> warehouseList;
    private Context context;


    public WarehouseAdapter(List<Warehouses> warehouseList, Context ctx) {
        this.warehouseList = warehouseList;
        this.context = ctx;

    }

    @NonNull
    @Override
    public GarageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_row, parent, false);
        GarageHolder holder = new GarageHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GarageHolder holder, int position) {
        final Warehouses warehouse = warehouseList.get(position);
        //holder._garageName.setText(garage.getName());
        holder.warehouseName.setText(warehouse.getmName());
        holder.warehouseBasecost.setText(warehouse.getmBaseCost()+"$");
        holder.warehouseAvailable.setText(warehouse.getmAvailable());
        holder.warehouseTaxpercent.setText(warehouse.getmTaxPercent()+"%");
        holder.warehouseDailyrate.setText(warehouse.getmDailyRate());
        //156.217.47.80:8000
        Picasso.get().load(R.drawable.warehouse_test).resize(108, 108).into(holder.warehouseImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(context, WarehouseProfile.class);
                goToProfile.putExtra("warehouseName",warehouse.getmName());
                goToProfile.putExtra("warehouse_id",warehouse.getmId());
                goToProfile.putExtra("warehouseAvailable",warehouse.getmAvailable());
                goToProfile.putExtra("warehouseBasecost",warehouse.getmBaseCost());
                goToProfile.putExtra("warehouseDailyrate",warehouse.getmDailyRate());
                goToProfile.putExtra("warehouseTaxpercent",warehouse.getmTaxPercent());
                context.startActivity(goToProfile);
            }
        });

    }

    @Override
    public int getItemCount() {
        return warehouseList.size();
    }


    class GarageHolder extends RecyclerView.ViewHolder {
        TextView warehouseName, warehouseBasecost, warehouseDailyrate, warehouseTaxpercent, warehouseAvailable;
        ImageView warehouseImage;
        CardView cardView;
        public GarageHolder(View itemView) {
            super(itemView);
            warehouseName = (TextView) itemView.findViewById(R.id.warehouse_name);
            warehouseBasecost = (TextView) itemView.findViewById(R.id.warehouse_basecost);
            warehouseDailyrate = (TextView) itemView.findViewById(R.id.warehouse_dailyrate);
            warehouseTaxpercent = (TextView) itemView.findViewById(R.id.warehouse_taxpercent);
            warehouseAvailable = (TextView) itemView.findViewById(R.id.warehouse_available);
            warehouseImage = (ImageView)itemView.findViewById(R.id.warehouse_image);
            cardView = (CardView) itemView.findViewById(R.id.warehouse_row_id);


        }
    }



}

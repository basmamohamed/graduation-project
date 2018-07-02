package com.example.madara.awsms.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madara.awsms.R;
import com.example.madara.awsms.WarehouseProfile;
import com.example.madara.awsms.models.OrderView;
import com.example.madara.awsms.models.Warehouses;
import com.squareup.picasso.Picasso;

import java.util.List;


public class UserWarehouseAdapter extends RecyclerView.Adapter<UserWarehouseAdapter.GarageHolder>{
    private static final String TAG = "GarageAdapter";
    private List<OrderView> orderViews;
    private Context context;


    public UserWarehouseAdapter(List<OrderView> orderViews, Context ctx) {
        this.orderViews = orderViews;
        this.context = ctx;

    }

    @NonNull
    @Override
    public GarageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_warehouse_row, parent, false);
        GarageHolder holder = new GarageHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GarageHolder holder, int position) {
        final OrderView warehouse = orderViews.get(position);
        //holder._garageName.setText(garage.getName());
        holder.warehouseId.setText(warehouse.getId());
        holder.warehousePrice.setText(warehouse.getPrice());
        holder.warehouseSpaces.setText(warehouse.getNumOfStorageSpaces());
        holder.warehouseDate.setText(warehouse.getDate());

    }

    @Override
    public int getItemCount() {
        return orderViews.size();
    }


    class GarageHolder extends RecyclerView.ViewHolder {
        TextView warehouseId, warehouseDate, warehousePrice, warehouseSpaces;
        ImageView warehouseImage;
        CardView cardView;
        public GarageHolder(View itemView) {
            super(itemView);
            warehouseId = itemView.findViewById(R.id.user_warehouse_id);
            warehouseDate = itemView.findViewById(R.id.user_warehouse_date);
            warehousePrice = itemView.findViewById(R.id.user_warehouse_price);
            warehouseSpaces = itemView.findViewById(R.id.user_warehouse_spaces);
            warehouseImage = itemView.findViewById(R.id.user_warehouse_image);

        }
    }



}

package com.example.madara.awsms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.madara.awsms.R;
import com.example.madara.awsms.WarehouseList;
import com.example.madara.awsms.models.OrderDetails;
import com.example.madara.awsms.models.WarehouseResponse;
import com.example.madara.awsms.models.Warehouses;

import java.util.ArrayList;

public class WarehouseAdapter extends ArrayAdapter<Warehouses> {
    public WarehouseAdapter(@NonNull WarehouseList warehouseList, ArrayList<Warehouses> warehouses) {
        super(warehouseList, 0, warehouses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);

    }

    public View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.warehoue_list, parent, false);
        }
        TextView baseCost = (TextView) convertView.findViewById(R.id.baseCosttxt);
        TextView dailyRate = (TextView) convertView.findViewById(R.id.dailyRatetxt);
        TextView taxPercent = (TextView) convertView.findViewById(R.id.taxPercenttxt);
        TextView name = (TextView) convertView.findViewById(R.id.nametxt);
        TextView available = (TextView) convertView.findViewById(R.id.availabletxt);

        Warehouses warehouses = getItem(position);


        if (warehouses != null) {
            taxPercent.setText(warehouses.getTaxPercent());
            baseCost.setText(warehouses.getBaseCost());
            dailyRate.setText(warehouses.getDailyRate());
            name.setText(warehouses.getName());
            available.setText(warehouses.getAvailable());
        }
        return convertView;
    }
}
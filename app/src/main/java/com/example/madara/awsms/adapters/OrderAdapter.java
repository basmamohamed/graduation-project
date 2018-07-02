//package com.example.madara.awsms.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.example.madara.awsms.R;
//import com.example.madara.awsms.models.OrderDetails;
//
//import java.util.ArrayList;
//
///**
// * Created by EGYTEK on 01/05/2018.
// */
//
//public class OrderAdapter extends ArrayAdapter <OrderDetails> {
//    private static final String LOG_TAG = OrderAdapter.class.getSimpleName();
//
//
//    public OrderAdapter(OrdersSummary ordersSummary, ArrayList<OrderDetails>orderDetails) {
//        super(ordersSummary,0,orderDetails);
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View listItemView = convertView;
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.list_item,parent ,false);
//        }
//        OrderDetails currentOrderDetails = getItem(position);
//
//
//        TextView numberOfUnitsTextView = (TextView) listItemView.findViewById(R.id.numberOfUnitsList);
//        numberOfUnitsTextView.setText(currentOrderDetails.getUnits());
//
//
//        TextView orderDateTextView = (TextView) listItemView.findViewById(R.id.orderDateList);
//        orderDateTextView.setText(currentOrderDetails.getOrderDate());
//
//
//        TextView deliverDateTextView = (TextView) listItemView.findViewById(R.id.deliverDateList);
//        deliverDateTextView.setText(currentOrderDetails.getDeliverDate());
//
//
//        TextView warehousesList = (TextView)listItemView.findViewById(R.id.WarehouseListSpinner);
//        //warehousesList.setText((CharSequence) currentOrderDetails.getWarehouses());
//        //warehousesList.setText(currentOrderDetails.getWarehouses().getName().toString());
//
//        return listItemView;
//    }
//    }
//
//

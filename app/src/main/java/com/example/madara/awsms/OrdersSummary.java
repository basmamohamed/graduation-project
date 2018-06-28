package com.example.madara.awsms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.madara.awsms.adapters.OrderAdapter;
import com.example.madara.awsms.models.OrderDetails;

import static com.example.madara.awsms.OrderActivity.Item;

public class OrdersSummary extends AppCompatActivity {
    private OrderDetails order;
    String units, orderDate, deliverDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_summary);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            order = bundle.getParcelable("order");
            item();

        }
    }
        public void item() {
            OrderAdapter OrderAdapter = new OrderAdapter(this, Item);
            ListView OrderList = (ListView) findViewById(R.id.summaryListView);
            OrderList.setAdapter(OrderAdapter);
        }

}

package com.joseph.wagba;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joseph.wagba.adapter.PastOrderAdapter;
import com.joseph.wagba.model.PastOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryPage extends AppCompatActivity {


    RecyclerView pastOrderRecycler;
    PastOrderAdapter pastOrderAdapter;
    List<PastOrder> pastOrders = new ArrayList<PastOrder>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);

        pastOrders.add(new PastOrder("Pizza Hut","Successful",R.drawable.pizza_hut_logo, formatter.format(date)));
        pastOrders.add(new PastOrder("Pizza Hut","Successful",R.drawable.papa, formatter.format(date)));

        setPastOrderRecycler(pastOrders);
    }



    public void setPastOrderRecycler(List<PastOrder> pastOrderList){

        pastOrderRecycler = findViewById(R.id.past_recy);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        pastOrderRecycler.setLayoutManager(layoutManager3);
        pastOrderAdapter = new PastOrderAdapter(pastOrderList,this);
        pastOrderRecycler.setAdapter(pastOrderAdapter);
    }
}

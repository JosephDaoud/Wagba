package com.joseph.wagba;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joseph.wagba.adapter.PastOrderAdapter;
import com.joseph.wagba.model.PastOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryPage extends AppCompatActivity {


    RecyclerView pastOrderRecycler;
    PastOrderAdapter pastOrderAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-b752c-default-rtdb.firebaseio.com/");
    DatabaseReference ordersRef = database.getReference("orders");
    DatabaseReference ordersContent = database.getReference("orderContents");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


    List<PastOrder> pastOrders = new ArrayList<PastOrder>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);

        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //System.out.println(dataSnapshot.getKey().toString().split("_")[0]);
                    System.out.println(firebaseAuth.getCurrentUser().getUid());
                    if (dataSnapshot.getKey().split("_")[0].equals(firebaseAuth.getCurrentUser().getUid())){
                        pastOrders.add(new PastOrder(dataSnapshot.child("restaurant").getValue().toString(),
                                dataSnapshot.child("status").getValue().toString(),"id: "+dataSnapshot.getKey().toString(),
                                dataSnapshot.getKey().toString().split("_")[1]));
                    }
                }
                setPastOrderRecycler(pastOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//


    }



    public void setPastOrderRecycler(List<PastOrder> pastOrderList){

        pastOrderRecycler = findViewById(R.id.past_recy);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        pastOrderRecycler.setLayoutManager(layoutManager3);
        pastOrderAdapter = new PastOrderAdapter(pastOrderList,this);
        pastOrderRecycler.setAdapter(pastOrderAdapter);
    }
}

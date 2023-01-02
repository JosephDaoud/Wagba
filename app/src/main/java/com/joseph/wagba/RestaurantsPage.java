package com.joseph.wagba;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joseph.wagba.adapter.MoreRestaurantAdapter;

import com.joseph.wagba.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsPage extends AppCompatActivity {
    RecyclerView restaurantRecycler;
    MoreRestaurantAdapter moreRestaurantAdapter;

    List<Restaurant> restaurantList = new ArrayList<Restaurant>();

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-b752c-default-rtdb.firebaseio.com/");
    DatabaseReference getRestaurants = database.getReference("restaurants");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants);



        getRestaurants.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    restaurantList.add(new Restaurant(dataSnapshot.child("name").getValue().toString()
                            ,(Long) dataSnapshot.child("rating").getValue(),
                            dataSnapshot.child("image").getValue().toString(),"",""));


                }
                setMoreRestaurantRecycler(restaurantList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }



    private void setMoreRestaurantRecycler(List<Restaurant> restaurantList){
        restaurantRecycler = findViewById(R.id.more_rest_recy);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        restaurantRecycler.setLayoutManager(layoutManager3);
        moreRestaurantAdapter = new MoreRestaurantAdapter(this,restaurantList);
        restaurantRecycler.setAdapter(moreRestaurantAdapter);


    }


}

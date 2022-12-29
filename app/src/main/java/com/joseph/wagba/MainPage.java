package com.joseph.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.joseph.wagba.adapter.BestPickAdapter;
import com.joseph.wagba.adapter.RestaurantAdapter;
import com.joseph.wagba.model.BestPicks;
import com.joseph.wagba.model.CartItem;
import com.joseph.wagba.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {


    RecyclerView bestPicksRecycler;
    BestPickAdapter bestPickAdapter;
    RecyclerView restaurantRecycler;
    RestaurantAdapter restaurantAdapter;


    TextView rest_seemore, mainPageUsername;
    ImageView cart, history, logout;


    Intent main2Res;
    Intent main2Cart;
    Intent main2History;




    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();



    FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-b752c-default-rtdb.firebaseio.com/");
    DatabaseReference usersReference = database.getReference("users");

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://wagba-b752c.appspot.com");
    StorageReference pizzaHutRef = storage.getReference("/images/pizzaHut.svg.png");
    StorageReference papaJohns = storage.getReference("/images/papa.png");
    StorageReference kfc = storage.getReference("/images/kfc.png");
    StorageReference fourxfour = storage.getReference("/images/4X4.jpg");
    StorageReference chickenRanch = storage.getReference("images/Chicken-Ranch.jpg");
    StorageReference meatLovers =  storage.getReference("images/meatlovers.jpeg");

    DatabaseReference getRestaurants = database.getReference("restaurants");
    DatabaseReference getBestPicks = database.getReference("bestpicks");

    MealsPage mealsPage;
    List<BestPicks> bestPicksList = new ArrayList<BestPicks>();
    List<Restaurant> restaurants = new ArrayList<Restaurant>();
    TinyDB tinyDB;


    @Override
    protected void onStart() {


        super.onStart();

        if (firebaseUser == null){
            startActivity(new Intent(this, LoginPage.class));
            finish();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tinyDB = new TinyDB(this);
        tinyDB.remove("cartObjects");
        tinyDB.remove("totalQuantity");
        tinyDB.clear();

    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);




        rest_seemore = findViewById(R.id.restaurant_seemore);
        cart = findViewById(R.id.cart_button);
        history = findViewById(R.id.past_orders);
        mainPageUsername = findViewById(R.id.mainpageUsername);
        logout = findViewById(R.id.logout);




        main2Res = new Intent(this, RestaurantsPage.class);
        main2Cart = new Intent(this, CartPage.class);
        main2History = new Intent(this,HistoryPage.class);



        tinyDB = new TinyDB(this);
        if (! tinyDB.preferences.contains("cartObjects")){
            ArrayList<Object> cartObjects = new ArrayList<Object>();
            tinyDB.putListObject("cartObjects",cartObjects);
        }
       if (! tinyDB.preferences.contains("totalQuantity")){
           int totalQuantity = 0;
           tinyDB.putInt("totalQuantity",totalQuantity);
       }

        setRestaurantRecycler(restaurants);






        getRestaurants.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(
                            @NonNull DataSnapshot dataSnapshot)
                    {

                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        restaurants.add(new Restaurant(dataSnapshot1.child("name").getValue().toString()
                                ,(Long) dataSnapshot1.child("rating").getValue(),
                                dataSnapshot1.child("image").getValue().toString(),"",""));
                        }
                        setRestaurantRecycler(restaurants);
                    }

                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(MainPage.this, "Error Loading Image", Toast.LENGTH_SHORT).show();
                    }
                });



        getBestPicks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot2 : snapshot.getChildren()){

                    bestPicksList.add(new BestPicks(dataSnapshot2.child("name").getValue().toString(),
                            dataSnapshot2.child("time").getValue().toString(),
                            dataSnapshot2.child("price").getValue().toString(),
                           dataSnapshot2.child("restaurant").getValue().toString(),
                            dataSnapshot2.child("image").getValue().toString()));
                }
                setBestPicksRecycler(bestPicksList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fname = snapshot.child(firebaseUser.getUid()).child("firstName").getValue().toString();
                if (firebaseUser != null){
                    mainPageUsername.setText(fname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        usersReference.addValueEventListener(eventListener);


        rest_seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(main2Res);

            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(main2Cart);

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(main2History);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainPage.this, LoginPage.class));
                finish();
            }
        });





    }

    private void setBestPicksRecycler(List<BestPicks> bestPicksList){
        bestPicksRecycler = findViewById(R.id.bestpicks_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        bestPicksRecycler.setLayoutManager(layoutManager);
        bestPickAdapter = new BestPickAdapter(this, bestPicksList);
        bestPicksRecycler.setAdapter(bestPickAdapter);
    }

    private void setRestaurantRecycler(List<Restaurant> restaurantList){
        restaurantRecycler = findViewById(R.id.rest_rv);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        restaurantRecycler.setLayoutManager(layoutManager2);
        restaurantAdapter = new RestaurantAdapter(this,restaurantList);
        restaurantRecycler.setAdapter(restaurantAdapter);

    }




}

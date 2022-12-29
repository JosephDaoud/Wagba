package com.joseph.wagba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.joseph.wagba.adapter.MealAdapter;
import com.joseph.wagba.model.CartItem;
import com.joseph.wagba.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsPage extends AppCompatActivity  {

    RecyclerView mainDishRecy;
    RecyclerView dessertsRecy;
    RecyclerView beveRecy;
    MealAdapter dessertsAdapter;
    MealAdapter beveAdapter;
    MealAdapter mealAdapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-b752c-default-rtdb.firebaseio.com/");
    DatabaseReference pizzaHutMainMeals = database.getReference("restaurants/1/meals/main");
    DatabaseReference pizzaHutBeverages = database.getReference("restaurants/1/meals/beverages");
    DatabaseReference pizzaHutDesserts = database.getReference("restaurants/1/meals/desserts");

    List<Meal> mainDishesList = new ArrayList<Meal>();
    List<Meal> beveragesList = new ArrayList<Meal>();
    List<Meal> dessertList = new ArrayList<Meal>();

    public   List<CartItem> cartItems = new ArrayList<CartItem>();
    public  ArrayList<Object> cartObjects = new ArrayList<Object>();
    public int quantity;


    TinyDB tinyDB ;
    Bundle extras ;
    public TextView noOfItemsText;
    TextView viewBasket;



    @Override
    protected void onStop() {
        super.onStop();


        cartObjects.clear();
        for (CartItem cartItem : cartItems){
            cartObjects.add((Object) cartItem);
        }
        tinyDB.putListObject("cartObjects",cartObjects);
        tinyDB.putInt("totalQuantity",quantity);
        cartObjects.clear();
    }






    @Override
    protected void onStart() {
        super.onStart();

        if (tinyDB.preferences.contains("cartObjects") ){
            cartObjects.clear();
            cartObjects = tinyDB.getListObject("cartObjects",CartItem.class);
            cartItems.clear();
            for (Object obj : cartObjects){
                cartItems.add((CartItem) obj);
            }
            quantity = tinyDB.getInt("totalQuantity");
            noOfItemsText.setText(String.valueOf(quantity));
            Log.e("scratch","on start -> mealspage " + "shared pref quantity"+ String.valueOf(quantity));
        }

    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_page);
        tinyDB = new TinyDB(this);
        extras = getIntent().getExtras();

        noOfItemsText = findViewById(R.id.noOfItemsMealP);
        noOfItemsText.setText(String.valueOf(tinyDB.getInt("totalItems")));
        viewBasket = findViewById(R.id.viewbasket);
        quantity = tinyDB.getInt("totalQuantity");




        switch (extras.getString("RestaurantName")){

            case "Pizza Hut":
                pullRestaurantMeals(pizzaHutMainMeals,mainDishesList,R.id.recy_mainDishes,mainDishRecy,mealAdapter);
                pullRestaurantMeals(pizzaHutBeverages,beveragesList,R.id.recy_bevarges,beveRecy,beveAdapter);
                pullRestaurantMeals(pizzaHutDesserts,dessertList,R.id.recy_desserts,dessertsRecy,dessertsAdapter );
                break;


            default:
                finish();


        }


        viewBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cartItems.size() !=0){
                    cartObjects.clear();
                    for (CartItem cartItem : cartItems){
                        cartObjects.add((Object) cartItem);
                    }
                    tinyDB.putListObject("cartObjects",cartObjects);
                    tinyDB.putInt("totalQuantity",quantity);
                }

                startActivity(new Intent(MealsPage.this,CartPage.class));
            }
        });


    }


    private void setMealRecycler(List<Meal> mealList,int resource, RecyclerView recyclerView, MealAdapter mealAdapter){

        recyclerView = findViewById(resource);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager3);
        mealAdapter= new MealAdapter(this,mealList,this);
        recyclerView.setAdapter(mealAdapter);


    }

    private void pullRestaurantMeals(DatabaseReference reference, List<Meal> meals, int id,RecyclerView recyclerView, MealAdapter mealAdapter){

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    meals.add(new Meal(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("content").getValue().toString(),
                            dataSnapshot.child("price").getValue().toString(),
                            dataSnapshot.child("image").getValue().toString()));
                }
                   setMealRecycler(meals, id,recyclerView,mealAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

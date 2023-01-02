package com.joseph.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.joseph.wagba.adapter.CartAdapter;
import com.joseph.wagba.model.CartItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CartPage extends AppCompatActivity {


    RecyclerView cartRecycler;
    CartAdapter cartAdapter;
    TextView basketTotal;
    TextView restaurantName;
    Button addItems;
    public  ScrollView scrollView;
    public CardView bottomCard;
    Button checkout;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-b752c-default-rtdb.firebaseio.com/");
    DatabaseReference ordersRef = database.getReference("orders");
    DatabaseReference ordersContent = database.getReference("orderContents");
    FirebaseAuth firebaseAuth;


    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    public int quantity;
    ArrayList<Object> cartObjects = new ArrayList<Object>();
    TinyDB tinyDB;

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (cartItems.size() != 0) {
            ArrayList<Object> cartobj = new ArrayList<Object>();
            for (CartItem cartItem : cartItems) {
                cartobj.add((Object) cartItem);
            }

            tinyDB.putListObject("cartObjects", cartobj);
            tinyDB.putInt("totalQuantity", quantity);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);
        firebaseAuth = FirebaseAuth.getInstance();

        addItems = findViewById(R.id.addItems);
        checkout = findViewById(R.id.checkoutButton);
        restaurantName = findViewById(R.id.card_rest_name);
        scrollView = findViewById(R.id.scrollCart);
        bottomCard = findViewById(R.id.bottomCardCart);

        tinyDB = new TinyDB(this);
        basketTotal = findViewById(R.id.basket_total);
        quantity = tinyDB.getInt("totalQuantity");


        cartObjects.clear();
        cartItems.clear();

        if (tinyDB.preferences.contains("cartObjects")) {

            cartObjects = tinyDB.getListObject("cartObjects", CartItem.class);
            Log.e("carttt", "on create if "+String.valueOf(cartObjects.size()));
            if (cartObjects.size() !=0){
                for (Object obj : cartObjects) {
                    cartItems.add((CartItem) obj);
                }
                restaurantName.setText(tinyDB.getString("currRestaurant"));
                setCartItemsRecycler(cartItems);
                calculateTotal(cartObjects);
            }
            else {
                bottomCard.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                restaurantName.setText("");
                basketTotal.setText(new StringBuilder().append("EGP ").append("0"));
            }

        }



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
                Log.e("restnamee",tinyDB.getString("currRestaurant"));


                for (Object obj : tinyDB.getListObject("cartObjects", CartItem.class)) {
                    cartItemArrayList.add((CartItem) obj);
                }
                for (int i = 0; i < cartItemArrayList.size(); i++) {
                    ordersContent.child(firebaseAuth.getCurrentUser().getUid()
                            +"_"+strDate).child(String.valueOf(i)).child("name").
                            setValue(cartItemArrayList.get(i).getName());
                    ordersContent.child(firebaseAuth.getCurrentUser().getUid()
                                    +"_"+strDate).child(String.valueOf(i)).child("quantity").
                            setValue(cartItemArrayList.get(i).getQuantity());
                }


                ordersRef.child(firebaseAuth.getCurrentUser().getUid()
                        + "_"+ strDate).child("total").setValue(String.valueOf(calcFTotal(cartItemArrayList)));
                ordersRef.child(firebaseAuth.getCurrentUser().getUid()
                        + "_"+ strDate).child("restaurant").
                        setValue(tinyDB.getString("currRestaurant"));
                ordersRef.child(firebaseAuth.getCurrentUser().getUid()
                                + "_"+ strDate).child("status").
                        setValue("pending");

                Toast.makeText(CartPage.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();


                tinyDB.putListObject("cartObjects",new ArrayList<Object>());
                tinyDB.putInt("totalQuantity",0);
                cartItems.clear();
                Log.e("carttt ", "checkout on click " + String.valueOf(tinyDB.getListObject("cartObjects",CartItem.class).size()));
                finish();


            }
        });

    }


    public void setCartItemsRecycler(List<CartItem> cartItems) {

        cartRecycler = findViewById(R.id.cart_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        cartRecycler.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this, cartItems, this);
        cartRecycler.setAdapter(cartAdapter);

    }
    public void calculateTotal(ArrayList<Object> cartObjects) {

        Float total = 0F;
        int quantity;
        float itemPrice;
        ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

        for (Object obj : cartObjects) {
            cartItems.add((CartItem) obj);
        }

        for (int i = 0; i < cartItems.size(); i++) {

            quantity = Integer.parseInt(String.valueOf(cartItems.get(i).getQuantity()));
            itemPrice = Float.parseFloat(cartItems.get(i).getTotalPrice().split(" ")[1]);
            total += (float) quantity * itemPrice;
        }

        basketTotal.setText(new StringBuilder().append("EGP ").append(total).toString());
    }
    public void clearRestaurantName(){

        restaurantName.setText("");

    }



public float calcFTotal (ArrayList<CartItem> cartItems) {
    float total = 0F;
    int quantity;
    float itemPrice;


    for (int i = 0; i < cartItems.size(); i++) {

        quantity = Integer.parseInt(String.valueOf(cartItems.get(i).getQuantity()));
        itemPrice = Float.parseFloat(cartItems.get(i).getTotalPrice().split(" ")[1]);
        total += (float) quantity * itemPrice;
    }

    return total;
}
}

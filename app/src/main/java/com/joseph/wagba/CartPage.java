package com.joseph.wagba;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joseph.wagba.adapter.CartAdapter;
import com.joseph.wagba.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends AppCompatActivity {


    RecyclerView cartRecycler;
    CartAdapter cartAdapter;
    TextView basketTotal;
    Button addItems;



    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    public int quantity;
    ArrayList<Object> cartObjects = new ArrayList<Object>();
    TinyDB tinyDB ;




    @Override
    protected void onStop() {
        super.onStop();


        if (cartItems.size() !=0) {
            ArrayList<Object> cartobj = new ArrayList<Object>();
            for (CartItem cartItem : cartItems) {
                cartobj.add((Object) cartItem);
            }

            tinyDB.putListObject("cartObjects", cartobj);
            tinyDB.putInt("totalQuantity",quantity);
            Log.e("scratch", (String.valueOf( cartobj.size())));
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);
        addItems = findViewById(R.id.addItems);
        tinyDB = new TinyDB(this);
        basketTotal = findViewById(R.id.basket_total);
        quantity = tinyDB.getInt("totalQuantity");


            cartObjects.clear();
            cartItems.clear();

            if (tinyDB.preferences.contains("cartObjects")) {
                cartObjects = tinyDB.getListObject("cartObjects",CartItem.class);
                for (Object obj : cartObjects) {
                    cartItems.add((CartItem) obj);
                }
                Log.e("scratch", String.valueOf(cartItems.size()));
                setCartItemsRecycler(cartItems);
                calculateTotal(cartObjects);
            }


    }


    public void setCartItemsRecycler(List<CartItem> cartItems){

        cartRecycler = findViewById(R.id.cart_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        cartRecycler.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this,cartItems,this);
        cartRecycler.setAdapter(cartAdapter);

    }


    public void calculateTotal(ArrayList<Object> cartObjects){

        Float total=0F;
        int quantity;
        float itemPrice;
        ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

        for (Object obj : cartObjects){
            cartItems.add((CartItem) obj);
        }

        for (int i = 0; i < cartItems.size(); i++) {

            quantity = Integer.parseInt(String.valueOf(cartItems.get(i).getQuantity()));
            itemPrice = Float.parseFloat(cartItems.get(i).getTotalPrice().split(" ")[1]);
            total += (float) quantity *itemPrice;
        }

       basketTotal.setText(new StringBuilder().append("EGP ").append(total).toString());
    }
}

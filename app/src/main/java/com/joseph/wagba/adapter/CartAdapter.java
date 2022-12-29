package com.joseph.wagba.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.joseph.wagba.CartPage;
import com.joseph.wagba.R;
import com.joseph.wagba.TinyDB;
import com.joseph.wagba.model.CartItem;

import java.util.ArrayList;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    Context context;
    List<CartItem> cartItems;
    TinyDB tinyDB;
    ArrayList<Object> cartObj ;
    ArrayList<CartItem> cartItemArrayList;
    CartPage cartPage;



    public CartAdapter(Context context, List<CartItem> cartItems,Activity activity) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartPage = (CartPage) activity;

    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHolder(LayoutInflater.from(context).inflate(R.layout.basket_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        holder.itemName.setText(cartItems.get(position).getName());
        holder.content.setText(cartItems.get(position).getContent());
        holder.totalPrice.setText(cartItems.get(position).getTotalPrice());
        holder.numberOfItems.setText(String.valueOf(cartItems.get(position).getQuantity()));



        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tinyDB = new TinyDB(context);
                cartObj = tinyDB.getListObject("cartObjects",CartItem.class);


                cartPage.cartItems.clear();
                for (Object obj : cartObj){
                    cartPage.cartItems.add((CartItem) obj);
                }
               for (CartItem cartItem : cartPage.cartItems){
                   if (cartItem.getName().equals(holder.itemName.getText())){
                       System.out.println("");

                       if (cartItem.getQuantity() == 1){
                           cartItem.setQuantity(cartItem.getQuantity()-1);
                           cartPage.quantity-=1;
                           cartPage.cartItems.remove(cartItem);
                           cartPage.setCartItemsRecycler(cartPage.cartItems);
                       }
                       else
                       {
                           cartItem.setQuantity(cartItem.getQuantity()-1);
                           holder.numberOfItems.setText(String.valueOf(cartItem.getQuantity()));
                           cartPage.quantity -=1;
                       }
                       cartObj.clear();
                       break;
                   }
               }

                cartObj.clear();
               for (CartItem cartItem : cartPage.cartItems){
                   cartObj.add((Object) cartItem);
               }
               tinyDB.putListObject("cartObjects",cartObj);
               tinyDB.putInt("totalQuantity",cartPage.quantity);
               cartPage.calculateTotal(tinyDB.getListObject("cartObjects",CartItem.class));

            }
        });

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tinyDB = new TinyDB(context);
                cartItemArrayList = new ArrayList<CartItem>();
                cartObj = tinyDB.getListObject("cartObjects",CartItem.class);

                cartPage.cartItems.clear();
                for (Object obj : cartObj){
                    cartPage.cartItems.add((CartItem) obj);
                }
                for (CartItem cartItem : cartPage.cartItems){
                    if (cartItem.getName().equals(holder.itemName.getText())){
                        cartItem.setQuantity(cartItem.getQuantity()+1);
                        holder.numberOfItems.setText(String.valueOf(cartItem.getQuantity()));
                        cartPage.quantity+=1;
                        break;
                    }
                }

                cartObj.clear();
                for (CartItem cartItem : cartPage.cartItems){
                    cartObj.add((Object) cartItem);
                }
                tinyDB.putListObject("cartObjects",cartObj);
                tinyDB.putInt("totalQuantity",cartPage.quantity);
                cartPage.calculateTotal(tinyDB.getListObject("cartObjects",CartItem.class));


            }
        });



    }

    @Override
    public int getItemCount() {
        return this.cartItems.size();
    }

    public static final class CartHolder extends RecyclerView.ViewHolder {


        TextView itemName;
        TextView content;
        Button plusButton;
        Button minusButton;
        TextView totalPrice;
        TextView numberOfItems;


        public CartHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.past_name);
            content = itemView.findViewById(R.id.cart_meal_content);
            plusButton = itemView.findViewById(R.id.plus_button);
            minusButton = itemView.findViewById(R.id.dec_button);
            totalPrice = itemView.findViewById(R.id.total_item_price);
            numberOfItems = itemView.findViewById(R.id.number_of_items);


        }
    }


}

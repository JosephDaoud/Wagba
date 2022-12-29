package com.joseph.wagba.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joseph.wagba.CartPage;
import com.joseph.wagba.MealsPage;
import com.joseph.wagba.R;
import com.joseph.wagba.TinyDB;
import com.joseph.wagba.model.CartItem;
import com.joseph.wagba.model.Meal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealHolder>{



    Context context;
    List<Meal> mealsList;



    TinyDB tinyDB;
    ArrayList<Object> cartObj = new ArrayList<Object>();
    Boolean flag = true;

    ArrayList<CartItem> cartMeals = new ArrayList<CartItem>();
    ArrayList<Object> cartMealsObj = new ArrayList<Object>();







    MealsPage mealsPage;



    public MealAdapter(Context context, List<Meal> mealsList,Activity activity) {
        this.context = context;
        this.mealsList=mealsList;
        this.mealsPage = (MealsPage) activity;
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealHolder(LayoutInflater.from(context).inflate(R.layout.menu_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {


        holder.mealContent.setText(mealsList.get(position).getName());
        holder.mealName.setText(mealsList.get(position).getName());
        Glide.with(context).load(mealsList.get(position).getImagePos()).into(holder.mealImg);
        holder.mealContent.setText(mealsList.get(position).getContent());
        holder.mealPrice.setText(mealsList.get(position).getPrice());








        holder.mealAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tinyDB = new TinyDB(context);




                if (mealsPage.cartItems.size() == 0){

                    mealsPage.cartItems.add(new CartItem(holder.mealName.getText().toString(),
                            holder.mealContent.getText().toString(),
                            "EGP "+holder.mealPrice.getText().toString(),1));
                    mealsPage.quantity +=1;
                }
                else{

                flag = true;
               for (CartItem cartItem: mealsPage.cartItems) {
                   if (cartItem.getName().equals(holder.mealName.getText())) {
                       cartItem.setQuantity(cartItem.getQuantity() + 1);
                       flag = false;
                       mealsPage.quantity +=1;
                       break;
                   }
               }

                    if (flag) {
                        mealsPage.cartItems.add(new CartItem(holder.mealName.getText().toString(),
                                holder.mealContent.getText().toString(),
                                "EGP " + holder.mealPrice.getText().toString(), 1));
                        mealsPage.quantity +=1;
                    }
                }
                tinyDB.putInt("totalQuantity",mealsPage.quantity);
                mealsPage.noOfItemsText.setText(String.valueOf(mealsPage.quantity));
            }
        });

    }



    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    public static final class MealHolder extends RecyclerView.ViewHolder {

        ImageView mealImg;
        TextView mealName;
        Button mealAddButton;
        TextView mealContent,mealPrice;
        LinearLayout mealHolder;




        public MealHolder(@NonNull View itemView) {
            super(itemView);

           mealImg = itemView.findViewById(R.id.past_image);
           mealName = itemView.findViewById(R.id.past_name);
           mealAddButton = itemView.findViewById(R.id.add_meal_button);
           mealContent = itemView.findViewById(R.id.cart_meal_content);
           mealPrice = itemView.findViewById(R.id.mealprice);
           mealHolder = itemView.findViewById(R.id.meal_content_holder);


        }
    }
}

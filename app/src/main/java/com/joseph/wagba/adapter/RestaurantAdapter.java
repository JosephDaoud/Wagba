package com.joseph.wagba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joseph.wagba.MealsPage;
import com.joseph.wagba.R;
import com.joseph.wagba.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {

    Context context;
    List<Restaurant> restaurantList;
    Intent restaurant2Meals;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {

        holder.itemName.setText(restaurantList.get(position).getName());
        holder.itemRating.setRating(restaurantList.get(position).getRating());
        holder.itemCategory.setText(restaurantList.get(position).getName());
        Glide.with(context).load(restaurantList.get(position).getImageURL()).into(holder.itemImage);
        holder.resConstraintLayout.setClickable(true);
        holder.resConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restaurant2Meals = new Intent(view.getContext(), MealsPage.class);
                restaurant2Meals.putExtra("RestaurantName",holder.itemName.getText());
                view.getContext().startActivity(restaurant2Meals);
            }
        });

    }



    @Override
    public int getItemCount() {
        return this.restaurantList.size();
    }

    public static final class RestaurantHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        RatingBar itemRating;
        ConstraintLayout resConstraintLayout;
        TextView itemCategory;


        public RestaurantHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.restaurant_image);
            itemName = itemView.findViewById(R.id.restaurant_name);
            itemRating = itemView.findViewById(R.id.restaurant_rating);
            resConstraintLayout = itemView.findViewById(R.id.rest_con_layout);
            itemCategory = itemView.findViewById(R.id.restaurant_category);





        }
    }
}

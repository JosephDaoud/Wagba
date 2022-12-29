package com.joseph.wagba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joseph.wagba.MealsPage;
import com.joseph.wagba.R;
import com.joseph.wagba.model.Restaurant;


import java.util.List;

public class MoreRestaurantAdapter extends RecyclerView.Adapter<MoreRestaurantAdapter.MoreRestaurantHolder> {


    Context context;
    List<Restaurant> moreRestaurantList;
    Intent restaurant2Meals;





    public MoreRestaurantAdapter(Context context, List<Restaurant> moreRestaurantList) {
        this.context = context;
        this.moreRestaurantList = moreRestaurantList;
    }

    @NonNull
    @Override
    public MoreRestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoreRestaurantHolder(LayoutInflater.from(context).inflate(R.layout.more_restaurants,parent,false));
    }




    @Override
    public void onBindViewHolder(@NonNull MoreRestaurantAdapter.MoreRestaurantHolder holder, int position) {

        holder.moreRestName.setText(moreRestaurantList.get(position).getName());
        holder.moreRestRating.setRating(moreRestaurantList.get(position).getRating());
       // holder.moreRestImg.setImageResource(moreRestaurantList.get(position).getImageURL());
        Glide.with(context).load(moreRestaurantList.get(position).getImageURL()).into(holder.moreRestImg);
        holder.moreRestCategory.setText(moreRestaurantList.get(position).getName());


        holder.restaurantCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //ToDo

            restaurant2Meals = new Intent(view.getContext(),MealsPage.class);
            restaurant2Meals.putExtra("RestaurantName",holder.moreRestName.getText());
            view.getContext().startActivity(restaurant2Meals);


            }
        });


    }



    @Override
    public int getItemCount() {
        return this.moreRestaurantList.size();
    }

    public static final class MoreRestaurantHolder extends RecyclerView.ViewHolder {

        ImageView moreRestImg;
        TextView moreRestName;
        RatingBar moreRestRating;
        CardView restaurantCard;
        TextView moreRestCategory;


        public MoreRestaurantHolder(@NonNull View itemView) {
            super(itemView);

            moreRestImg = itemView.findViewById(R.id.past_image);
            moreRestName = itemView.findViewById(R.id.past_name);
            moreRestRating = itemView.findViewById(R.id.more_rest_rating);
            restaurantCard = itemView.findViewById(R.id.more_rest_card);
            moreRestCategory = itemView.findViewById(R.id.past_status);





        }
    }
}

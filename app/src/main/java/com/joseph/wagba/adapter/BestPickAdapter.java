package com.joseph.wagba.adapter;

import android.content.Context;
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
import com.google.android.material.transition.Hold;
import com.joseph.wagba.R;
import com.joseph.wagba.model.BestPicks;

import java.util.List;

public class BestPickAdapter extends RecyclerView.Adapter<BestPickAdapter.BestPickHolder> {




    Context context;
    List<BestPicks> bestPicksList;

    public BestPickAdapter(Context context, List<BestPicks> bestPicksList) {
        this.context = context;
        this.bestPicksList = bestPicksList;
    }

    @NonNull
    @Override
    public BestPickHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BestPickHolder(LayoutInflater.from(context).inflate(R.layout.bestpicks_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BestPickHolder holder, int position) {

        //holder.itemImage.setImageResource(bestPicksList.get(position).getImageUrl());
        Glide.with(context).load(bestPicksList.get(position).getImageUrl()).into(holder.itemImage);
        holder.itemName.setText(bestPicksList.get(position).getName());
        holder.itemTime.setText(bestPicksList.get(position).getTime());
        holder.restaurantName.setText(bestPicksList.get(position).getRestaurant());

        holder.itemPrice.setText(bestPicksList.get(position).getPrice());
        holder.constraintLayout.setClickable(true);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "Clicking on "+ holder.itemName.getText();
                Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.bestPicksList.size();
    }

    public static final class BestPickHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemPrice,itemTime,itemName,restaurantName;

        ConstraintLayout constraintLayout;

        public BestPickHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemPrice = itemView.findViewById(R.id.itemprice);
            itemTime = itemView.findViewById(R.id.itemtime);
            itemName = itemView.findViewById(R.id.itemname);
            restaurantName = itemView.findViewById(R.id.mealRestaurant);
            constraintLayout = itemView.findViewById(R.id.conlayout);




        }
    }


}

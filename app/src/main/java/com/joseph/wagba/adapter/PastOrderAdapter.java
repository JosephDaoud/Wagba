package com.joseph.wagba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseph.wagba.R;
import com.joseph.wagba.model.PastOrder;

import java.util.List;

public class PastOrderAdapter extends RecyclerView.Adapter<PastOrderAdapter.PastOrderHolder>{


    List<PastOrder> pastOrders;
    Context context;

    public PastOrderAdapter(List<PastOrder> pastOrders, Context context) {
        this.pastOrders = pastOrders;
        this.context = context;
    }


    @NonNull
    @Override
    public PastOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  PastOrderHolder(LayoutInflater.from(context).inflate(R.layout.pastorder_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderHolder holder, int position) {

        holder.pastResName.setText(pastOrders.get(position).getRestaurantName());
        holder.status.setText(pastOrders.get(position).getStatus());
        holder.orderid.setText(pastOrders.get(position).getImagePos());
        holder.pastDate.setText(pastOrders.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return pastOrders.size();
    }

    public static final class PastOrderHolder extends RecyclerView.ViewHolder {

        TextView orderid;
        TextView pastResName;
        TextView pastDate;
        TextView status;




        public PastOrderHolder(@NonNull View itemView) {
            super(itemView);

            orderid = itemView.findViewById(R.id.orderid);
            pastResName = itemView.findViewById(R.id.past_name);
            pastDate = itemView.findViewById(R.id.past_date);
            status = itemView.findViewById(R.id.past_status);





        }
    }
}

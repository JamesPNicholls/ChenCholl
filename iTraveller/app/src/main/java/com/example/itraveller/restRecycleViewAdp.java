package com.example.itraveller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class restRecycleViewAdp extends RecyclerView.Adapter<restRecycleViewAdp.myViewHolder> {
    Context context;
    ArrayList<CRestaurant> restaurants;

    public restRecycleViewAdp(Context context, ArrayList<CRestaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;


    }

    @NonNull
    @Override
    public restRecycleViewAdp.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rest_row, parent, false);

        return new restRecycleViewAdp.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull restRecycleViewAdp.myViewHolder holder, int position) {
        // Assign values to each row, based on pos
        holder.tvName.setText(restaurants.get(position).getName());
        holder.tvAddress.setText(restaurants.get(position).getAddress());
        holder.tvType.setText(restaurants.get(position).getType());
        holder.imageView.setImageResource(R.drawable.china);


    }

    @Override
    public int getItemCount() {
        //how muych stuff do you have

        return restaurants.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        //grabs all the data and assigns it to the views

        ImageView imageView;
        TextView  tvName, tvAddress, tvType;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvName    = itemView.findViewById(R.id.textViewName);
            tvAddress = itemView.findViewById(R.id.textViewAddress);
            tvType    =itemView.findViewById(R.id.textViewType);


        }
    }
}

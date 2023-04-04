package com.example.itraveller;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class restRecycleViewAdp extends RecyclerView.Adapter<restRecycleViewAdp.myViewHolder> {
    private final restRecycleInterface restRecycleInterface;
    Context context;
    ArrayList<CRestaurant> restaurants;

    public restRecycleViewAdp(Context context, ArrayList<CRestaurant> restaurants, restRecycleInterface restRecycleInterface ) {
        this.context = context;
        this.restaurants = restaurants;
        this.restRecycleInterface = restRecycleInterface;


    }

    @NonNull
    @Override
    public restRecycleViewAdp.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rest_row, parent, false);

        return new restRecycleViewAdp.myViewHolder(view, restRecycleInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull restRecycleViewAdp.myViewHolder holder, int position) {
        // Assign values to each row, based on pos
        holder.tvName.setText(restaurants.get(position).getName());
        holder.tvAddress.setText(restaurants.get(position).getAddress());
        holder.tvType.setText(restaurants.get(position).getType());
        holder.tvRating.setText(Float.toString(restaurants.get(position).getRating()));

        holder.tvCard.setRadius(10);

        String img = restaurants.get(position).getType();
    }

    @Override
    public int getItemCount() {
        //how muych stuff do you have

        return restaurants.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        //grabs all the data and assigns it to the views

        TextView  tvName, tvAddress, tvType, tvRating;
        CardView  tvCard;


        public myViewHolder(@NonNull View itemView, restRecycleInterface restRecycleInterface) {
            super(itemView);

            tvCard  = itemView.findViewById(R.id.parent);
            tvRating = itemView.findViewById(R.id.textRating);
            tvName    = itemView.findViewById(R.id.textViewName);
            tvAddress = itemView.findViewById(R.id.textViewAddress);
            tvType    =itemView.findViewById(R.id.textViewType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (restRecycleInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            restRecycleInterface.onItemClick(position);
                        }
                    }
                }
            });


        }
    }
}

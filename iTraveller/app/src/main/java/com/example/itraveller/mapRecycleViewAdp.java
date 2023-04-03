package com.example.itraveller;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarException;

public class mapRecycleViewAdp extends RecyclerView.Adapter<mapRecycleViewAdp.MyViewHolder>{
    Context context;
    ArrayList<CRestImage> cRestImages;

    private final mapRecycleInterface  mapInterface;


    public mapRecycleViewAdp(mapRecycleInterface mapInterface, Context context, ArrayList<CRestImage> cRestImages){
        this.context = context;
        this.cRestImages = cRestImages;
        this.mapInterface = mapInterface;
    }


     @NonNull
     @Override
    public mapRecycleViewAdp.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater inflater =LayoutInflater.from(context);
         View view = inflater.inflate(R.layout.phot_row, parent, false);

        return new mapRecycleViewAdp.MyViewHolder(view,  mapInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull mapRecycleViewAdp.MyViewHolder holder, int position) {

        holder.netView.setImageBitmap(cRestImages.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return cRestImages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView netView;

        public MyViewHolder(@NonNull View itemView, mapRecycleInterface mapInterface) {
            super(itemView);

            netView = itemView.findViewById(R.id.netView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mapInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mapInterface.onItemClick(position, view);
                        }
                    }
                }
            });
        }
    }
}

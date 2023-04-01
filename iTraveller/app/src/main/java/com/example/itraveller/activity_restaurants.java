package com.example.itraveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class activity_restaurants extends AppCompatActivity {
//https://maps.googleapis.com/maps/api/place/photo
//  ?maxwidth=400
//  &photo_reference=AUjq9jnSmTPXy9LfVCNx75qICEJCdqTB0SKnJUq5ZeuzqoVrAMlVtdVML4wcbGbKlZJSIOSIrbMnCnc4u7_mxr0Uk66RFE7Y6JfvVSEtnNRSP1-iK6mmsBRE696MsIWn9UeXyn_LYokz63VCXc_XZSW1XbPD5ZnLK2mf4pyUKc50BCnYk9s&key=YOUR_API_KEY

    //get the data from the previous page

    private urlMaker url = new urlMaker();
    //url.urlSet("")



    String tempUrl = url.getUrl();

    ArrayList<CRestaurant> _restaurants = new ArrayList<CRestaurant>();


    //Unpack the data from previous activity


    String hotel_name;
    String[] hotelLL;
    ArrayList<String> rest_type;


    RequestQueue queue;
    JsonObjectRequest request;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        RecyclerView recView = findViewById(R.id.restRecycleView);
        TextView textHotel = findViewById(R.id.textHotel);



        Intent intent = getIntent();
        hotel_name = intent.getStringExtra("hotel_name");
        hotelLL = intent.getStringArrayExtra("latLng");
        rest_type = intent.getStringArrayListExtra("rest_list");

        textHotel.setText(hotel_name);

        if(rest_type.size() == 0){
            url.urlSet(hotelLL[0],hotelLL[1], "1000", "restaurant", "");
            sendAndRequestResponse(url.getUrl());
        } else{
            for(String type : rest_type){
                url.urlSet(hotelLL[0],hotelLL[1], "1000", "restaurant", type);
                sendAndRequestResponse(url.getUrl());
            }
        }


    }

    public void handleResponse(JSONObject results){

        try{
            JSONArray jsonResults = results.getJSONArray("results");
            for(int i = 0; i < jsonResults.length(); i++){
                JSONObject jo = jsonResults.getJSONObject(i);
                JSONArray  photos = jo.getJSONArray("photos");
                JSONObject ref = photos.getJSONObject(0);

                CRestaurant rest = new CRestaurant( jo.getString("name"),
                        jo.getString("vicinity"),
                        "chinese",
                        ref.getString("photo_reference")

                );
                _restaurants.add(rest);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void sendAndRequestResponse(String url){

        queue = Volley.newRequestQueue(this);
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RecyclerView recView = findViewById(R.id.restRecycleView);
                handleResponse(response);
                restRecycleViewAdp adapter = new restRecycleViewAdp(activity_restaurants.this, _restaurants);

                recView.setAdapter(adapter);
                recView.setLayoutManager(new LinearLayoutManager(activity_restaurants.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity_restaurants.this, "nope", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
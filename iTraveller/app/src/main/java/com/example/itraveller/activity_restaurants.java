package com.example.itraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;




public class activity_restaurants extends AppCompatActivity implements restRecycleInterface {
//https://maps.googleapis.com/maps/api/place/photo
//  ?maxwidth=400
//  &photo_reference=AUjq9jnSmTPXy9LfVCNx75qICEJCdqTB0SKnJUq5ZeuzqoVrAMlVtdVML4wcbGbKlZJSIOSIrbMnCnc4u7_mxr0Uk66RFE7Y6JfvVSEtnNRSP1-iK6mmsBRE696MsIWn9UeXyn_LYokz63VCXc_XZSW1XbPD5ZnLK2mf4pyUKc50BCnYk9s&key=YOUR_API_KEY

    //get the data from the previous page

    private urlMaker _urlMaker = new urlMaker();
    //url.urlSet("")

    ArrayList<CRestaurant> _restaurants = new ArrayList<CRestaurant>();


    //Unpack the data from previous activity


    String hotel_name;
    String hotel_ref;
    String[] hotelLL;
    String place_id;
    String search_rad;

    ArrayList<String> rest_type;


    RequestQueue queue;
    JsonObjectRequest request;
    ImageRequest requestIm;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        RecyclerView recView = findViewById(R.id.restRecycleView);
        TextView textHotel = findViewById(R.id.textHotel);


        Intent intent = getIntent();
        hotel_name = intent.getStringExtra("hotel_name");
        hotel_ref  = intent.getStringExtra("hotel_ref");
        hotelLL = intent.getStringArrayExtra("latLng");
        rest_type = intent.getStringArrayListExtra("rest_list");
        search_rad = Integer.toString(intent.getIntExtra("search_radius",50))  ;

        textHotel.setText(hotel_name);

        // Populate the restaurants based on type
        if(!hotel_name.equals("Current Location"))
        {
            getHotelImage(hotel_ref);
        }

            if(rest_type.size() == 0){
                _urlMaker.urlSet(hotelLL[0],hotelLL[1], search_rad, "restaurant", "");
                sendAndRequestResponse(_urlMaker.getUrl(""), "");
                sendAndRequestResponse(_urlMaker.getUrl(""), "");
            } else{
                for(String type : rest_type){
                    _urlMaker.urlSet(hotelLL[0],hotelLL[1], search_rad, "restaurant", type);
                    sendAndRequestResponse(_urlMaker.getUrl("restaurant"),type);
                }
            }




        // Set the image of the chosen hotel


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:{
                        Intent intent = new Intent(activity_restaurants.this, Hotels.class);
                        startActivity(intent);
                        Toast.makeText(activity_restaurants.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.logout:{
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(activity_restaurants.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });


    }

    public void handleResponse(JSONObject results, String type){

        try{


            JSONArray jsonResults = results.getJSONArray("results");

            if( (jsonResults.length()==0) && (rest_type.size() == 0) )
            {

                CRestaurant rest = new CRestaurant( "No Restaurants found...",
                        "",
                        "empty",
                        "",
                        new String[]{},
                        "",
                        0);

                _restaurants.add(rest);

            } else if(jsonResults.length()!=0){

                for(int i = 0; i < jsonResults.length(); i++) {
                    JSONObject jo = jsonResults.getJSONObject(i);
                    JSONObject geo = jo.getJSONObject("geometry");
                    JSONObject loc = geo.getJSONObject("location");


                    JSONArray photos = jo.getJSONArray("photos");
                    JSONObject ref = photos.getJSONObject(0);


                    CRestaurant rest = new CRestaurant(jo.getString("name"),
                            jo.getString("vicinity"),
                            type,
                            ref.getString("photo_reference"),
                            new String[]{loc.getString("lat"),loc.getString("lng")},
                            jo.getString("place_id"),
                            (float) jo.getDouble("rating")
                            );

                    _restaurants.add(rest);
                }

                }else{
                CRestaurant rest = new CRestaurant( "No Restaurants found for Type: ",
                        type,
                        "empty",
                        "",
                        new String[]{},
                        "",
                        0);

                _restaurants.add(rest);
                }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }


    public void sendAndRequestResponse(String url, String type){

        queue = Volley.newRequestQueue(this);
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RecyclerView recView = findViewById(R.id.restRecycleView);
                handleResponse(response, type);

                restRecycleViewAdp adapter = new restRecycleViewAdp(activity_restaurants.this, _restaurants, activity_restaurants.this);

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

    public void getHotelImage(String ref){
        String url = _urlMaker.getPicUrl(ref,400);
        queue = Volley.newRequestQueue(this);
        ImageView imageView = findViewById(R.id.hotel_image);
        requestIm = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 100, 100, imageView.getScaleType(), Bitmap.Config.ALPHA_8,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activity_restaurants.this, "happy acddients", Toast.LENGTH_SHORT).show();

                }
            });

        queue.add(requestIm);

    }

    @Override
    public void onItemClick(int pos) {
            if(!_restaurants.get(pos).getType().equals("empty")){
                Intent intent = new Intent(activity_restaurants.this, MapsActivity.class);
                intent.putExtra("hotel_name", hotel_name);
                intent.putExtra("rest_name", _restaurants.get(pos).getName());
                intent.putExtra("hotel_LL", hotelLL);
                intent.putExtra("rest_LL", _restaurants.get(pos).getLatLong());
                intent.putExtra("place_id", _restaurants.get(pos).getPlaceid());
                intent.putExtra("search_radius", search_rad);
                startActivity(intent);
            } else{
                Toast.makeText(this, "Choose a valid restaurant...", Toast.LENGTH_SHORT).show();
            }


    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

}


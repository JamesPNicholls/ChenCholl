package com.example.itraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.itraveller.databinding.ActivityRegisterBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.itraveller.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class  MapsActivity extends AppCompatActivity implements OnMapReadyCallback, mapRecycleInterface {
=======
public class  MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
>>>>>>> 430abf0594d66ca3f677df045ac90aaac8ab49a5

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String[] rest_LL, hotel_LL;
    private String   hotel_Name, rest_Name, place_id;
    int searchRad;

    private urlMaker _urlMaker = new urlMaker();

    private restDetails _restDetails = new restDetails();
    private ArrayList<CRestImage> cRestImages = new ArrayList<>();

    PopupWindow popupWindow;
    ImageView popView;


    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ArrayList<PopupWindow> popupWindowArrayList = new ArrayList<PopupWindow>();


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

        Intent intent = getIntent();
        rest_LL  = intent.getStringArrayExtra("rest_LL");
        hotel_LL = intent.getStringArrayExtra("hotel_LL");
        hotel_Name = intent.getStringExtra("hotel_name");
        rest_Name  = intent.getStringExtra("rest_name");
        place_id   = intent.getStringExtra("place_id");
        searchRad = Integer.valueOf(intent.getStringExtra("search_radius"));

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getRestDetails();

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
                        Intent intent = new Intent(MapsActivity.this, Hotels.class);
                        startActivity(intent);
                        Toast.makeText(MapsActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.logout:{
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(MapsActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        direction();
    }

    private void direction(){
        requestQueue = Volley.newRequestQueue(this);
        String dest = rest_LL[0] + ", " + rest_LL[1];
        String orig = hotel_LL[0] + ", " + hotel_LL[1];

        String url = Uri.parse("https://maps.googleapis.com/maps/api/directions/json")
                .buildUpon()
                .appendQueryParameter("destination", dest)
                .appendQueryParameter("origin", orig)
                .appendQueryParameter("mode", "walking")
                .appendQueryParameter("key", "AIzaSyAczDSfmACyCZjZVmk5cu0XYJ-lkYihAp4")
                .toString();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("OK")) {
                        JSONArray routes = response.getJSONArray("routes");

                        ArrayList<LatLng> points;
                        PolylineOptions polylineOptions = null;

                        for (int i=0;i<routes.length();i++){
                            points = new ArrayList<>();
                            polylineOptions = new PolylineOptions();
                            JSONArray legs = routes.getJSONObject(i).getJSONArray("legs");

                            for (int j=0;j<legs.length();j++){
                                JSONArray steps = legs.getJSONObject(j).getJSONArray("steps");

                                for (int k=0;k<steps.length();k++){
                                    String polyline = steps.getJSONObject(k).getJSONObject("polyline").getString("points");
                                    List<LatLng> list = decodePoly(polyline);

                                    for (int l=0;l<list.size();l++){
                                        LatLng position = new LatLng((list.get(l)).latitude, (list.get(l)).longitude);
                                        points.add(position);
                                    }
                                }
                            }
                            polylineOptions.addAll(points);
                            polylineOptions.width(10);
                            polylineOptions.color(ContextCompat.getColor(MapsActivity.this, R.color.purple_500));
                            polylineOptions.geodesic(true);
                        }
                        mMap.addPolyline(polylineOptions);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(rest_LL[0]), Double.valueOf(rest_LL[1]))).title(rest_Name));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(hotel_LL[0]), Double.valueOf(hotel_LL[1]))).title(hotel_Name));

                        LatLngBounds bounds = new LatLngBounds.Builder()
                                .include(new LatLng(Double.valueOf(rest_LL[0]), Double.valueOf(rest_LL[1])))
                                .include(new LatLng(Double.valueOf(hotel_LL[0]), Double.valueOf(hotel_LL[1]))).build();
                        Point point = new Point();


                        getWindowManager().getDefaultDisplay().getSize(point);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, point.x, 150, 30));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RetryPolicy retryPolicy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonObjectRequest);
    }
    private ArrayList<LatLng> decodePoly(String encoded) {

        Log.i("Location", "String received: "+encoded);
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),(((double) lng / 1E5)));
            poly.add(p);
        }

        for(int i=0;i<poly.size();i++){
            Log.i("Location", "Point sent: Latitude: "+poly.get(i).latitude+" Longitude: "+poly.get(i).longitude);
        }
        return poly;
    }

    public void getRestDetails() {
        requestQueue = Volley.newRequestQueue(this);
        String url = _urlMaker.getRestInfoUrl(place_id);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject results = response.getJSONObject("result");
                    _restDetails.setDetails(results);
                    displayRestDetails();

                    // Needs to be called after the photo_ids have been gerneated
                    setUpImage();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        requestQueue.add(jsonObjectRequest);
    }

    public void displayRestDetails(){
      TextView textNameMap= findViewById(R.id.textNameMap);
      TextView textAddressMap= findViewById(R.id.textAddressMap);
      TextView textUrlMap= findViewById(R.id.textUrlMap);
      TextView textPhoneMap= findViewById(R.id.textPhoneMap);
      TextView textReviewText= findViewById(R.id.textReviewText);
      TextView textHoursMap= findViewById(R.id.textHoursMap);
      TextView textOC= findViewById(R.id.textOC);

      RatingBar ratingBar = findViewById(R.id.ratingBar);

      textNameMap.setText( _restDetails.getName());
      textAddressMap.setText( _restDetails.getAddress());
      textUrlMap.setText( _restDetails.getWebsite());
      textPhoneMap.setText( _restDetails.getPhoneNum());
      textReviewText.setText( _restDetails.getReviewText());
      textHoursMap.setText( _restDetails.getWeekdayText());

      ratingBar.setRating((float) _restDetails.getRating());

        if (_restDetails.isOpen()){
            textOC.setText( "OPEN");
        } else {
            textOC.setText("CLOSED");
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

    // Gets the bitmap images with volley request based on the photo reference strings
    // When the all images are stored popualte the recycle view with them
    void setUpImage(){

        for(String photo_id : _restDetails.getPhotoRef()){
            String url = _urlMaker.getPicUrl(photo_id,1000);

            requestQueue = Volley.newRequestQueue(this);

            ImageRequest  imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    try {

                        cRestImages.add(new CRestImage(response, url ));
                        if(cRestImages.size() == _restDetails.getPhotoRef().size()){

                            RecyclerView recyclerView = findViewById(R.id.recMapImage);
                            mapRecycleViewAdp adapter = new mapRecycleViewAdp(MapsActivity.this, MapsActivity.this, cRestImages);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this, LinearLayoutManager.HORIZONTAL,false));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                },1000,1000, Bitmap.Config.ALPHA_8,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(imageRequest);



        }

    }

    @Override
    public void onItemClick(int pos, View view) {
       // On click event when the
        String url = cRestImages.get(pos).getUrl();
        if(popupWindow == null){
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.img_popout, null);
            popupWindow = new PopupWindow(popupView, 1000,1000, false);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0,0);

            popView = (ImageView) popupView.findViewById(R.id.popImgView);
            popView.setImageBitmap(cRestImages.get(pos).getImg());

            popView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            });

        }



    }
}

package com.example.itraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.DecimalFormat;

public class Hotels extends AppCompatActivity {

    EditText etCity;
    TextView tvResult;
    Button btnGetData;
    Double lat;
    Double lng;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";
    DecimalFormat df = new DecimalFormat("#.##");
    JsonObjectRequest request2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        etCity = findViewById(R.id.city);
        tvResult = findViewById(R.id.result);
        btnGetData = findViewById(R.id.btnGetData);
        btnGetData.setOnClickListener(view -> {
            tvResult.setText("");
            String tempUrl = "";




            String CoordName = etCity.getText().toString().trim();

            CoordName = CoordName.replace(" ", "+");


            if (CoordName.equals("")) {
                tvResult.setText("Hotel field can not be empty!");

            } else {
                tempUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+CoordName+"&key=AIzaSyAczDSfmACyCZjZVmk5cu0XYJ-lkYihAp4";
            }
            RequestQueue queue = Volley.newRequestQueue(Hotels.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tempUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {


                        JSONArray coord = response.getJSONArray("results");
                        JSONObject jsonObjectCoord = coord.getJSONObject(0);
                        JSONObject jsonObjectgeometry =  jsonObjectCoord.getJSONObject("geometry");
                        JSONObject jsonObjectlocation = jsonObjectgeometry.getJSONObject("location");
                        lat = jsonObjectlocation.getDouble("lat");
                        lng = jsonObjectlocation.getDouble("lng");



                        tvResult.setText(

                                "Description: " + lat + "\n" +lng


                        );


                        queue.add(request2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Hotels.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);


            tempUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=resturant&location="+lat+",%20"+lng+"&radius=5000&key=AIzaSyAczDSfmACyCZjZVmk5cu0XYJ-lkYihAp4";


            //queue = Volley.newRequestQueue(MainActivity.this);
            request2 = new JsonObjectRequest(Request.Method.GET, tempUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {


                        JSONArray hotels = response.getJSONArray("results");
                        JSONObject jsonObjectWeather = hotels.getJSONObject(0);
                        String lat = jsonObjectWeather.getString("name");






                        tvResult.setText(
                                "Description: " + lat + "\n"
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Hotels.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });


        });




    }
}
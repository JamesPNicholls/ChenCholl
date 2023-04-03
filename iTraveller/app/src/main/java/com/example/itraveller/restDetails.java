package com.example.itraveller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class restDetails {
    private String website;
    private String name;
    private String address;
    private String reviewText;
    private String weekdayText = "";



    private String phoneNum;

    private ArrayList<String> photoRef;

    private double rating, reviewRate;

    private boolean isOpen;
    public restDetails() {
        photoRef = new ArrayList<String>();
    }

    public void setDetails(JSONObject object){

        try{
            name = object.getString("name");

            try{
                website = object.getString("website");
            } catch (JSONException e){
                website = "n/a";
            }
            address = object.getString("vicinity");
            phoneNum = object.getString("international_phone_number");
            rating = object.getDouble("rating") ;

            // Gets current open
            JSONObject openHours = object.getJSONObject("current_opening_hours");
            isOpen = openHours.getBoolean("open_now");

            // Gets the opening hours
            JSONObject openH = object.getJSONObject("opening_hours");
            JSONArray  weekD = openH.getJSONArray("weekday_text");

            String[] days = {"M","T","W","T","F","S","S"};
            for(int i = 0; i< weekD.length(); i++){
                String[] temp = weekD.getString(i).split(" ");
                weekdayText += temp[0].toUpperCase(Locale.ROOT) + "\n"+ temp[1] +"\n\n";
            }

            // Gets the first review rating and text present
            JSONArray review = object.getJSONArray("reviews");
            reviewText     = review.getJSONObject(0).getString("text");
            reviewRate = review.getJSONObject(0).getDouble("rating");

            // Gets photo reference code
            JSONArray photos = object.getJSONArray("photos");
            for(int i = 0; i < photos.length(); i++){
                photoRef.add( photos.getJSONObject(i).getString("photo_reference"));
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public ArrayList<String> getPhotoRef() {
        return photoRef;
    }

    public String getWebsite() {
        return website;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getWeekdayText() {
        return weekdayText;
    }

    public double getRating() {
        return rating;
    }

    public double getReviewRate() {
        return reviewRate;
    }
}

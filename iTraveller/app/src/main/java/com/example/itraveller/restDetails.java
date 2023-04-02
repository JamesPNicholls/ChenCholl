package com.example.itraveller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class restDetails {
    private String website, name, address, reviewText;
    private String[] weekdayText, photoRef;
    private  double rating, reviewRate;

    public restDetails() {
    }

    public void setDetails(JSONObject object){

        try{
            name = object.getString("name");
            website = object.getString("website");
            address = object.getString("vicinity");

            rating = object.getDouble("rating") ;

            JSONObject openH = object.getJSONObject("opening_hours");
            JSONArray  weekD = openH.getJSONArray("weekday_text");
            weekdayText = new String[weekD.length()];
            for(int i = 0; i< weekD.length(); i++){
                weekdayText[i] = weekD.getString(i);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }




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

    public String[] getWeekdayText() {
        return weekdayText;
    }

    public double getRating() {
        return rating;
    }

    public double getReviewRate() {
        return reviewRate;
    }
}

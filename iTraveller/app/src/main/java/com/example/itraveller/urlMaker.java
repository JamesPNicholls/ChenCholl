package com.example.itraveller;

public class urlMaker {

    // Default data for sake of
    private String urlStart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private String key      = "AIzaSyA56SPjWPDegiQPfZgQBofAgd5TwCMej6E";
    private String locationLAT ;
    private String locationLON ;
    private String radius     ;
    private String searchType ;
    private String keyWord    ;
    public String url;

    // Default Generator
    public urlMaker() {
         locationLAT  = "49.1996869";
         locationLON   = "-122.9126745";
         radius     = "1000";
         searchType = "restaurant";
         keyWord    = "";//searches for all restaurants
    }

    public urlMaker(String locationLAT, String locationLON, String radius, String searchType, String keyWord) {
        this.locationLAT = locationLAT;
        this.locationLON = locationLON;
        this.radius = radius;
        this.searchType = searchType;
        this.keyWord = keyWord;
    }

    public void urlSet(String locationLAT, String locationLON, String radius, String searchType, String keyWord) {
        this.locationLAT = locationLAT;
        this.locationLON = locationLON;
        this.radius = radius;
        this.searchType = searchType;
        this.keyWord = keyWord;
    }

    public String getUrl( ){
        url = urlStart
            + "location="+locationLAT+"%2C"+locationLON
            + "&radius="+radius
            + "&type="+searchType
            + "&keyword="+keyWord
            + "&key="+key;
        return url;
    }
}

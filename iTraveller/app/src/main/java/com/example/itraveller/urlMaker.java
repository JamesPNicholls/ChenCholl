package com.example.itraveller;

public class urlMaker {

    // Default data for sake of

    private String urlStartRest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private String urlStartPhot = "https://maps.googleapis.com/maps/api/place/photo?";
    private String urlStartData = "https://maps.googleapis.com/maps/api/place/details/json?"; // for rest details on the map page
    private String key      = "AIzaSyA56SPjWPDegiQPfZgQBofAgd5TwCMej6E";
    private String locationLAT ;
    private String locationLON ;
    private String radius     ;
    private String searchType ;
    private String keyWord    ;
    private String photo_reference;
    public String url;

    // Default Generator
    public urlMaker() {
         locationLAT  = "49.1996869";
         locationLON   = "-122.9126745";
         radius     = "1000";
         searchType = "restaurant";
         photo_reference = "";
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


    public String getRestInfoUrl( String id){
        String url = urlStartData
                    + "placeid=" + id
                    + "&key=" + key;
        return url;
    }
    public String getPicUrl(String ref){
        String ret = urlStartPhot
                + "maxwidth=100"
                    + "&photo_reference=" + ref
                + "&key="+key;
        return ret;
    }
    public String getUrl(String type){

            url = urlStartRest
                    + "location="+locationLAT+"%2C"+locationLON
                    + "&radius="+radius
                    + "&type="+searchType
                    + "&keyword="+keyWord
                    + "&key="+key;


        return url;
    }
}

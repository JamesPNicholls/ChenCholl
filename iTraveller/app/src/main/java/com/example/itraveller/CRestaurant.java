package com.example.itraveller;

public class CRestaurant {

    String name, address, type, image;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=49.1996869%2C-122.9126745&radius=1000&type=restaurant&keyword=chinese|japanese&key=AIzaSyA56SPjWPDegiQPfZgQBofAgd5TwCMej6E
    public CRestaurant(String name, String address, String type, String image) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.image = image;
    }
}

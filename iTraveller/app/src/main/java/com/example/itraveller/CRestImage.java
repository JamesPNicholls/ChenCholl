package com.example.itraveller;

import android.graphics.Bitmap;

class CRestImage {
    Bitmap img;
    String url;

    public CRestImage(Bitmap img, String url) {
        this.img = img;
        this.url = url;
    }

    public Bitmap getImg() {
        return img;
    }
    public String getUrl() {
        return url;
    }
}

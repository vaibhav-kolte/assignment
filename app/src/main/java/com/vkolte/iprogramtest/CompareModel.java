package com.vkolte.iprogramtest;

import android.util.Log;

public class CompareModel
{
    private static final String TAG = "CompareModel";
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;
    private boolean isCompare;

    public CompareModel(int id, String title, String url, String thumbnailUrl, boolean isCompare) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.isCompare = isCompare;
        Log.e(TAG, "CompareModel: "+
        "id : "+ id +
        "title : "+ title +
        "url : "+ url +
        "thumbailURL : "+ thumbnailUrl +
        "isCoampare : "+isCompare
                );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isCompare() {
        return isCompare;
    }

    public void setCompare(boolean compare) {
        isCompare = compare;
    }
}
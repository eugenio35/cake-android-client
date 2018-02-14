package com.waracle.androidtest.model;

/**
 * Created by emancebo on 2/13/18.
 */

public class Cake {

    private String title;
    private String description;
    private String imageUrl;

    public Cake(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
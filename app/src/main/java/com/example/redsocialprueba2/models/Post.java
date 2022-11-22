package com.example.redsocialprueba2.models;

import android.widget.ImageView;

public class Post {
    private String id;
    private String idUser;
    private String title;
    private String description;
    private String mImagen1;
    private String mImager2;
    private String category;
    private long timestamp;

    public Post(){}

    public Post(String id, String title, String description, String mImagen1, String mImager2, String category, long timestamp) {
        this.timestamp = timestamp;
        this.id = id;
        this.title = title;
        this.description = description;
        this.mImagen1 = mImagen1;
        this.mImager2 = mImager2;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getmImagen1() {
        return mImagen1;
    }

    public void setmImagen1(String mImagen1) {
        this.mImagen1 = mImagen1;
    }

    public String getmImager2() {
        return mImager2;
    }

    public void setmImager2(String mImager2) {
        this.mImager2 = mImager2;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

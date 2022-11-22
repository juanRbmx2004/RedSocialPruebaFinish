package com.example.redsocialprueba2.models;

public class User {
    private String id;
    private String Email;
    private String UserName;
    private String UserPhone;
    private String image_perfil;
    private String image_cover;
    private long timestamp;
    public User(){

    }

    public User(String id, String email, String userName, String userPhone, long timestamp, String image_cover, String image_perfil ){
        this.timestamp = timestamp;
        this.UserPhone = userPhone;
        this.id = id;
        this.Email = email;
        this.UserName = userName;
        this.image_cover = image_cover;
        this.image_perfil = image_perfil;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserName() {
        return UserName;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage_perfil() {
        return image_perfil;
    }

    public void setImage_perfil(String image_perfil) {
        this.image_perfil = image_perfil;
    }

    public String getImage_cover() {
        return image_cover;
    }

    public void setImage_cover(String image_cover) {
        this.image_cover = image_cover;
    }
}

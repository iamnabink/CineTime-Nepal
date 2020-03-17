package com.whoamie.cinetime_nepal.member.models;

public class User {

    private String created_at;
    private String firebase_reg_id;
    private String bio;
    private double lon;
    private double lat;
    private String profile_pic_url;
    private String email;
    private String name;
    private int id;

    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFirebase_reg_id() {
        return firebase_reg_id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }


    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

}

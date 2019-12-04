package com.example.cinetime_nepal.common.models;

public class Movie {
    private String name;
    private int img_url;
    private int genre;

    public Movie(String name, int img_url, int genre) {
        this.name = name;
        this.img_url = img_url;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg_url() {
        return img_url;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }
}

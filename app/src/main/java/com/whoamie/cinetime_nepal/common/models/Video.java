package com.whoamie.cinetime_nepal.common.models;

public class Video {

    private int type;
    private String title;
    private String thumbnail_url;
    private String youtube_url;
    private int id;

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Video(int type, String title, String thumbnail_url, String youtube_url, int id) {
        this.type = type;
        this.title = title;
        this.thumbnail_url = thumbnail_url;
        this.youtube_url = youtube_url;
        this.id = id;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public String getYoutube_url() {
        return youtube_url;
    }

    public int getId() {
        return id;
    }
}

package com.whoamie.cinetime_nepal.member.models;

public class Review {

    private com.whoamie.cinetime_nepal.member.models.User user;
    private String comment_time;
    private int rating_count;
    private String comment_msg;
    private int movie_id;
    private int user_id;
    private int id;

    public User getUser() {
        return user;
    }

    public String getComment_time() {
        return comment_time;
    }

    public int getRating_count() {
        return rating_count;
    }

    public String getComment_msg() {
        return comment_msg;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

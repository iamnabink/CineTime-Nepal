package com.whoamie.cinetime_nepal.member.models;

public class MyReview {

    private com.whoamie.cinetime_nepal.common.models.Movie movie;
    private String created_at;
    private int rating_count;
    private String comment_msg;
    private int movie_id;
    private int user_id;
    private int id;

    public com.whoamie.cinetime_nepal.common.models.Movie getMovie() {
        return movie;
    }

    public String getCreated_at() {
        return created_at;
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
}

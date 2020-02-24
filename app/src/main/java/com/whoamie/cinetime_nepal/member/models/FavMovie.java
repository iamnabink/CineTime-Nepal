package com.whoamie.cinetime_nepal.member.models;

import com.whoamie.cinetime_nepal.common.models.Movie;

public class FavMovie {

    private int movie_id;
    private int user_id;
    private int id;
    private Movie movie;

    public int getMovie_id() {
        return movie_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }
}

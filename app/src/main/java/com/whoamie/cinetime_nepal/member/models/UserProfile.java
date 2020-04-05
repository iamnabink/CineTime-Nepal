package com.whoamie.cinetime_nepal.member.models;

import java.util.List;

public class UserProfile {

    private List<FavMovie> fav_movie;
    private List<Review> reviews;
    private User user;

    public List<FavMovie> getFav_movie() {
        return fav_movie;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public User getUser() {
        return user;
    }

}

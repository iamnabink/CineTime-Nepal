package com.whoamie.cinetime_nepal.common.models;

import java.util.List;

public class ShowTime {

    private Hall hall;
    private List<Time> times;
    private String show_date;
    private int hall_id;
    private int movie_id;
    private int id;

    public Hall getHall() {
        return hall;
    }

    public List<Time> getTimes() {
        return times;
    }

    public String getShow_date() {
        return show_date;
    }

    public int getHall_id() {
        return hall_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getId() {
        return id;
    }

}

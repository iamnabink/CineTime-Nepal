package com.whoamie.cinetime_nepal.common.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.models.Movie;

import java.util.ArrayList;

public class UserFavMovieFragment extends Fragment {
    ArrayList<Movie> movies;
    View view;
    public UserFavMovieFragment(ArrayList<Movie> movies) {
        this.movies=movies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_fav_movie, container, false);
        initViews();
        initVars();
        return view;
    }

    private void initVars() {

    }

    private void initViews() {

    }
}

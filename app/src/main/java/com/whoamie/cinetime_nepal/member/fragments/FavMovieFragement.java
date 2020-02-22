package com.whoamie.cinetime_nepal.member.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whoamie.cinetime_nepal.R;

public class FavMovieFragement extends Fragment {
    int userId;

//    public FavMovieFragement(int userId) {
//        this.userId = userId;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }
}

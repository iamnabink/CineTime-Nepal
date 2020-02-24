package com.whoamie.cinetime_nepal.member.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whoamie.cinetime_nepal.R;

public class FavMovieFragement extends Fragment {
    int userId;
    View view;
    RecyclerView recyclerView;

    public FavMovieFragement(int userId) {
        this.userId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        initVars();
        callgetfavMovieApi();
        return  view;
    }

    private void initVars() {
        recyclerView=view.findViewById(R.id.movie_user_recycler);

    }

    private void callgetfavMovieApi() {

    }
}

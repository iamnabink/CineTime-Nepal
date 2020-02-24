package com.whoamie.cinetime_nepal.member.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.member.adapters.FavMovieAdapter;
import com.whoamie.cinetime_nepal.member.models.FavMovie;

import java.util.ArrayList;

public class FavMovieFragement extends Fragment {
    int userId;
    View view;
    RecyclerView recyclerView;
    FavMovieAdapter adapter;
    ArrayList<FavMovie> favMovies = new ArrayList<>();

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

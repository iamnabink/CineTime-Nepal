package com.example.cinetime_nepal.common.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.adapter.ComingMovieAdapter;
import com.example.cinetime_nepal.common.models.Movie;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    View view;
    RecyclerView showsShowingRecyclerV,showsComingRecyclerV;
    ArrayList<Movie> movies = new ArrayList<>();
    ComingMovieAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        intiVar();
        initViews();
        loadData();
        return view;
    }

    private void loadData() {
        movies.add(new Movie("Khupi ka bau",R.drawable.avatar,"Danger Comdey"));
        movies.add(new Movie("Khupi ka bau",R.drawable.naruto,"Danger Comdey"));
        movies.add(new Movie("Khupi ka bau",R.drawable.avatar,"Danger Comdey"));
        movies.add(new Movie("Khupi ka bau",R.drawable.naruto,"Danger Comdey"));
    }

    private void initViews() {
        adapter=new ComingMovieAdapter(getContext(),movies);
        showsComingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        showsShowingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        showsComingRecyclerV.setAdapter(adapter);
        showsShowingRecyclerV.setAdapter(adapter);
    }

    private void intiVar() {
        showsShowingRecyclerV=view.findViewById(R.id.shows_showing_recycler_v);
        showsComingRecyclerV=view.findViewById(R.id.shows_coming_recycler_v);
    }
}

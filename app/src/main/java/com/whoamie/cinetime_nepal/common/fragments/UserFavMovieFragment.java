package com.whoamie.cinetime_nepal.common.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.MovieDetailActivity;
import com.whoamie.cinetime_nepal.common.adapter.UserFavMovieAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;

import java.util.ArrayList;

public class UserFavMovieFragment extends Fragment {
    ArrayList<Movie> movies;
    View view;
    LinearLayout emptyLayout;
    RecyclerView recyclerView;
    UserFavMovieAdapter adapter;
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
        recyclerView=view.findViewById(R.id.show_user_recycler);
        emptyLayout=view.findViewById(R.id.show_fav_movie_empty_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter=new UserFavMovieAdapter(movies, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Movie movie = movies.get(position);
                String movieDetails = new Gson().toJson(movie);
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(SharedPref.key_shared_movies_details, movieDetails);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
        if (adapter.getItemCount()==0){
            emptyLayout.setVisibility(View.VISIBLE);
        }
        else {
            emptyLayout.setVisibility(View.GONE);
        }
    }

    private void initViews() {

    }
}

package com.example.cinetime_nepal.common.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.adapter.ComingMovieAdapter;
import com.example.cinetime_nepal.common.models.Movie;
import com.example.cinetime_nepal.common.network.API;
import com.example.cinetime_nepal.common.network.RestClient;

import org.json.JSONObject;

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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.nowShowingMovieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server error ! Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        RestClient.getInstance(getContext()).addToRequestQueue(request);
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

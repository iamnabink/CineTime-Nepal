package com.example.cinetime_nepal.common.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.cinetime_nepal.common.adapter.ShowingMovieAdapter;
import com.example.cinetime_nepal.common.models.Movie;
import com.example.cinetime_nepal.common.network.API;
import com.example.cinetime_nepal.common.network.RestClient;
import com.example.cinetime_nepal.common.utils.CustomDialog;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    View view;
    RecyclerView showsShowingRecyclerV, showsComingRecyclerV;
    ArrayList<Movie> umovies, smovies = new ArrayList<>();
    ComingMovieAdapter uadapter;
    ShowingMovieAdapter sadapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CustomDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        intiVar();
        loadDataUpComing();
        loadDataShowing();
        initViews();
        return view;
    }

    private void intiVar() {
        showsShowingRecyclerV = view.findViewById(R.id.shows_showing_recycler_v);
        showsComingRecyclerV = view.findViewById(R.id.shows_coming_recycler_v);
    }

    private void initViews() {
        uadapter = new ComingMovieAdapter(getContext(), umovies);
        sadapter = new ShowingMovieAdapter(smovies, getContext());
        showsComingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        showsShowingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        showsComingRecyclerV.setAdapter(uadapter);
        showsShowingRecyclerV.setAdapter(sadapter);
    }

    private void loadDataShowing() {
        if (umovies != null) {
            smovies.clear();
        }
        dialog = new CustomDialog(getContext());
        dialog.show();
        preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref, Context.MODE_PRIVATE);
        editor = preferences.edit();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.nowShowingMovieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject moviesDetails = response.getJSONObject(SharedPref.key_data_details);
                    JSONArray moviesArrayData = moviesDetails.getJSONArray(SharedPref.key_shared_movies_details);
                    for (int i = 0; i < moviesArrayData.length(); i++) {
                        JSONObject moviesData = moviesArrayData.getJSONObject(i);
                        Movie movie = new Gson().fromJson(moviesData.toString(), Movie.class);
                        smovies.add(movie);
                        sadapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server error ! please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        RestClient.getInstance(getContext()).addToRequestQueue(request);
    }

    private void loadDataUpComing() {
        if (umovies != null) {
            umovies.clear();
        }
        dialog = new CustomDialog(getContext());
        dialog.show();
        preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref, Context.MODE_PRIVATE);
        editor = preferences.edit();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.upcomingMovieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.cancel();
                try {
                    JSONObject dataObject = response.getJSONObject(SharedPref.key_data_details);
                    JSONArray movieDataArray = dataObject.getJSONArray(SharedPref.key_shared_movies_details);
                    for (int i = 0; i < movieDataArray.length(); i++) {
                        JSONObject movieObject = movieDataArray.getJSONObject(i);
                        Movie movie = new Gson().fromJson(movieObject.toString(), Movie.class);
                        umovies.add(movie);
                        uadapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server error ! Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        RestClient.getInstance(getContext()).addToRequestQueue(request);
    }

}

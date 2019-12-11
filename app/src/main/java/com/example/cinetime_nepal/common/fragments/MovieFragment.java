package com.example.cinetime_nepal.common.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.activities.MovieDetailActivity;
import com.example.cinetime_nepal.common.adapter.ComingMovieAdapter;
import com.example.cinetime_nepal.common.adapter.ShowingMovieAdapter;
import com.example.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.example.cinetime_nepal.common.models.Movie;
import com.example.cinetime_nepal.common.network.API;
import com.example.cinetime_nepal.common.network.RestClient;
import com.example.cinetime_nepal.common.utils.CustomDialog;
import com.example.cinetime_nepal.common.utils.InternetConnectionCheck;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.google.gson.Gson;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class MovieFragment extends Fragment {
    RecyclerView showsShowingRecyclerV, showsComingRecyclerV;
    ArrayList<Movie> umovies = new ArrayList<>();
    ArrayList<Movie> smovies = new ArrayList<>();
    ComingMovieAdapter uadapter;
    ShowingMovieAdapter sadapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CustomDialog dialog;
    View noInternetView,view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        intiVar();
        initViews();
        listeners();
        loadDataShowing();
        loadDataUpComing();
        return view;
    }

    private void listeners() {
        noInternetView.findViewById(R.id.button_try_again) //button doesn't need to be cast since it doesn't have any specific property (button edit text should be casted)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDataShowing();
                        loadDataUpComing();
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        },4000);
                    }
                });
    }

    private void intiVar() {
        noInternetView = view.findViewById(R.id.view_no_internet);
        showsShowingRecyclerV = view.findViewById(R.id.shows_showing_recycler_v);
        showsComingRecyclerV = view.findViewById(R.id.shows_coming_recycler_v);
        dialog = new CustomDialog(getContext());
        preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref, Context.MODE_PRIVATE);
    }

    private void initViews() {
        uadapter = new ComingMovieAdapter(getContext(), umovies);
        sadapter = new ShowingMovieAdapter(smovies, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                startActivity(new Intent(getContext(), MovieDetailActivity.class));
            }
        });
        showsComingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        showsShowingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        showsComingRecyclerV.setAdapter(uadapter);
        showsShowingRecyclerV.setAdapter(sadapter);
    }

    private void loadDataShowing() {
        smovies.clear();
        dialog.show();
        editor = preferences.edit();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.nowShowingMovieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    JSONObject moviesDetails = response.getJSONObject(SharedPref.key_data_details);
                    JSONArray moviesArrayData = moviesDetails.getJSONArray(SharedPref.key_shared_movies_details);
                    String smoviesArray = moviesArrayData.toString();
                    editor.putString(SharedPref.key_shared_showing_movies_details, smoviesArray);
                    editor.apply();
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
                dialog.cancel();
                handlerError(error);
            }
        });
        if (InternetConnectionCheck.isNetworkAvailable(getContext())) {
            noInternetView.setVisibility(View.GONE);
            RestClient.getInstance(getContext()).addToRequestQueue(request);
        } else {
            noInternetView.setVisibility(View.VISIBLE);
            dialog.dismiss();
        }
    }

    private void loadDataUpComing() {
        umovies.clear();
        dialog.show();
        editor = preferences.edit();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.upcomingMovieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    JSONObject dataObject = response.getJSONObject(SharedPref.key_data_details);
                    JSONArray movieDataArray = dataObject.getJSONArray(SharedPref.key_shared_movies_details);
                    String umoviedataArray = movieDataArray.toString();
                    editor.putString(SharedPref.key_shared_upcoming_movies_details, umoviedataArray);
                    editor.apply();
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
                System.out.println("error"+error);
                dialog.dismiss();
                handlerError(error);
            }
        });
        if (InternetConnectionCheck.isNetworkAvailable(getContext())) {
            noInternetView.setVisibility(View.GONE);
            RestClient.getInstance(getContext()).addToRequestQueue(request);
        } else {
            noInternetView.setVisibility(View.VISIBLE);
            dialog.dismiss();
        }
    }

    private void handlerError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            //This indicates that the reuest has either time out or there is no connection
            Toast.makeText(getContext(), "time out or there is no connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(getContext(), "an Authentication Failure while performing the request", Toast.LENGTH_SHORT).show();
            //Error indicating that there was an Authentication Failure while performing the request
        } else if (error instanceof ServerError) {
            Toast.makeText(getContext(), "server responded with a error response", Toast.LENGTH_SHORT).show();
            //Indicates that the server responded with a error response
        } else if (error instanceof NetworkError) {
            Toast.makeText(getContext(), "network error while performing the request", Toast.LENGTH_SHORT).show();
            //Indicates that there was network error while performing the request
        } else if (error instanceof ParseError) {
            Toast.makeText(getContext(), "network error while performing the request", Toast.LENGTH_SHORT).show();
        }
    }


}

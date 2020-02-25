package com.whoamie.cinetime_nepal.member.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.adapters.FavMovieAdapter;
import com.whoamie.cinetime_nepal.member.models.FavMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavMovieFragement extends Fragment {
    View view;
    RecyclerView recyclerView;
    FavMovieAdapter adapter;
    ArrayList<FavMovie> favMovies = new ArrayList<>();
    Context context;


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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        adapter = new FavMovieAdapter(favMovies, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {

            }
        });
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context=context;
        super.onAttach(context);
    }

    private void callgetfavMovieApi() {
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(getContext(), Request.Method.POST, API.getfavMovieDetail, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    if (response.getBoolean("status")){
                        JSONArray jsonArray = response.getJSONArray(SharedPref.key_data_details);
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            FavMovie favMovie = new Gson().fromJson(object.toString(),FavMovie.class);
                            favMovies.add(favMovie);
                            adapter.notifyDataSetChanged();
                        }
//                    }
//                    else {
//                        Toast.makeText(getContext(), "Unauthorized access", Toast.LENGTH_SHORT).show();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                HandleNetworkError.handlerError(error,context);
            }
        });
//        RestClient.getInstance(getContext()).addToRequestQueue(request);
        if (CheckConnectivity.isNetworkAvailable(context)){
            RestClient.getInstance(getContext()).addToRequestQueue(request);
        }
        else {
            Toast.makeText(getContext(), "No internet Available", Toast.LENGTH_SHORT).show();
        }
    }

}

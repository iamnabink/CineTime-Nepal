package com.whoamie.cinetime_nepal.common.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.MovieDetailActivity;
import com.whoamie.cinetime_nepal.common.adapter.ComingMovieAdapter;
import com.whoamie.cinetime_nepal.common.adapter.ShowingMovieAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.ProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    RecyclerView showsShowingRecyclerV, showsComingRecyclerV;
    ArrayList<Movie> umovies = new ArrayList<>();
    ArrayList<Movie> smovies = new ArrayList<>();
    ComingMovieAdapter uadapter;
    ShowingMovieAdapter sadapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;
    View noInternetView, view;
    SwipeRefreshLayout refreshLayout;
    private Context mContext;
    ShimmerFrameLayout shimmerFrameLayoutU,shimmerFrameLayoutS;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        intiVar();
        initViews();
        listeners();
        loadMovieData();
        onRefresh();
        return view;
    }


    private void onRefresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dialog.show();
                loadMovieData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void listeners() {
        noInternetView.findViewById(R.id.button_try_again) //button doesn't need to be cast since it doesn't have any specific property (but note: EditText should be casted)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadMovieData();
                    }

                });
    }
    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayoutS.startShimmer();
        shimmerFrameLayoutU.startShimmer();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayoutS.stopShimmer();
        shimmerFrameLayoutU.stopShimmer();
    }
    private void intiVar() {
        shimmerFrameLayoutS=view.findViewById(R.id.shmovie_shimmer_layout);
        shimmerFrameLayoutU=view.findViewById(R.id.upmovie_shimmer_layout);
        refreshLayout = view.findViewById(R.id.swipe_refresh_l);
        noInternetView = view.findViewById(R.id.view_no_internet);
        showsShowingRecyclerV = view.findViewById(R.id.shows_showing_recycler_v);
        showsComingRecyclerV = view.findViewById(R.id.shows_coming_recycler_v);
        dialog = new ProgressDialog(mContext);
//        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 800);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref, Context.MODE_PRIVATE);
    }

    private void initViews() {
        uadapter = new ComingMovieAdapter(getContext(), umovies, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Movie movie = umovies.get(position);
                String movieDetails = new Gson().toJson(movie);
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(SharedPref.key_shared_movies_details, movieDetails);
                startActivity(intent);
            }
        });
        sadapter = new ShowingMovieAdapter(smovies, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Movie movie = smovies.get(position);
                String movieDetails = new Gson().toJson(movie);
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(SharedPref.key_shared_movies_details, movieDetails);
                startActivity(intent);
            }
        });
        showsComingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        showsShowingRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        showsComingRecyclerV.setAdapter(uadapter);
        showsShowingRecyclerV.setAdapter(sadapter);
    }

    private void loadMovieData() {
        smovies.clear();
        umovies.clear();
        shimmerFrameLayoutU.startShimmer();
        shimmerFrameLayoutS.startShimmer();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.getMoviesDetail, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                shimmerFrameLayoutS.stopShimmer();
                shimmerFrameLayoutS.setVisibility(View.GONE);
                shimmerFrameLayoutU.stopShimmer();
                shimmerFrameLayoutU.setVisibility(View.GONE);
                dialog.dismiss();
                try {
                    JSONArray showingMovieList = response.getJSONArray("showing");
                    for (int i = 0; i < showingMovieList.length(); i++) {
                        JSONObject movieObject = showingMovieList.getJSONObject(i);
                        Movie movie = new Gson().fromJson(movieObject.toString(), Movie.class);
                        smovies.add(movie);
                    }
                    if (sadapter.getItemCount()==0){
                        showsShowingRecyclerV.setVisibility(View.GONE);
                        view.findViewById(R.id.empty_layout_smoviefrag).setVisibility(View.VISIBLE);
                    }
                    else {
                        showsShowingRecyclerV.setVisibility(View.VISIBLE);
                        view.findViewById(R.id.empty_layout_smoviefrag).setVisibility(View.GONE);
                    }
                    sadapter.notifyDataSetChanged();
                    JSONArray comingMoviesList = response.getJSONArray("coming");
                    for (int i = 0; i < comingMoviesList.length(); i++) {
                        JSONObject movieObject = comingMoviesList.getJSONObject(i);
                        Movie movie = new Gson().fromJson(movieObject.toString(), Movie.class);
                        umovies.add(movie);
                    }
                    if (uadapter.getItemCount()==0){
                        showsComingRecyclerV.setVisibility(View.GONE);
                        view.findViewById(R.id.empty_layout_cmoviefrag).setVisibility(View.VISIBLE);
                    }
                    else {
                        showsComingRecyclerV.setVisibility(View.VISIBLE);
                        view.findViewById(R.id.empty_layout_cmoviefrag).setVisibility(View.GONE);
                    }
                    uadapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                shimmerFrameLayoutS.stopShimmer();
                shimmerFrameLayoutS.setVisibility(View.GONE);
                shimmerFrameLayoutU.stopShimmer();
                shimmerFrameLayoutU.setVisibility(View.GONE);
                HandleNetworkError.handlerError(error, mContext);
                dialog.dismiss();
            }
        });
        if (CheckConnectivity.isNetworkAvailable(mContext)) {
            noInternetView.setVisibility(View.GONE);
            RestClient.getInstance(getContext()).addToRequestQueue(request);
        } else {
            noInternetView.setVisibility(View.VISIBLE);
            dialog.dismiss();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context; //prevents context from being null (since getContext sometimes get null)
    }
}

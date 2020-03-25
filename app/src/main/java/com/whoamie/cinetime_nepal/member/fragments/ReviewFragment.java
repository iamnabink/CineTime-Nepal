package com.whoamie.cinetime_nepal.member.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.member.adapters.FavMovieAdapter;
import com.whoamie.cinetime_nepal.member.adapters.MyReviewAdapter;
import com.whoamie.cinetime_nepal.member.models.FavMovie;
import com.whoamie.cinetime_nepal.member.models.MyReview;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {
    View view;
    Context context;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    MyReviewAdapter adapter;
    ArrayList<MyReview> reviews = new ArrayList<>();
//    public ProfileReviewFragment(int userId) {
//        this.userId = userId;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_review, container, false);
        initVars();
        initViews();
        clickListner();
        loadReviews();
        return view;
    }

    private void initVars() {

    }

    private void initViews() {

    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        shimmerFrameLayout.startShimmer();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        shimmerFrameLayout.stopShimmer();
//    }

    private void clickListner() {

    }
    @Override
    public void onAttach(@NonNull Context context) {
        this.context=context;
        super.onAttach(context);
    }
    private void loadReviews() {
        AuthenticatedJSONRequest authenticatedJSONRequest = new AuthenticatedJSONRequest(getContext(), Request.Method.POST, API.getUserReview, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        if (CheckConnectivity.isNetworkAvailable(context)){
            RestClient.getInstance(getContext()).addToRequestQueue(authenticatedJSONRequest);
        }
        else {
            Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }
}

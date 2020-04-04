package com.whoamie.cinetime_nepal.member.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
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
import com.whoamie.cinetime_nepal.member.adapters.MyReviewAdapter;
import com.whoamie.cinetime_nepal.member.models.FavMovie;
import com.whoamie.cinetime_nepal.member.models.MyReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {
    View view;
    Context context;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    MyReviewAdapter adapter;
    ArrayList<MyReview> reviews = new ArrayList<>();
    LinearLayout linearLayout;
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
        linearLayout = view.findViewById(R.id.review_empty_layout);
        recyclerView=view.findViewById(R.id.user_review_recycler);
        adapter = new MyReviewAdapter(reviews, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
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
                try {
                    if (response.getBoolean("status")){
                        Activity activity = getActivity();
                        if (activity != null && isAdded()){
                        JSONArray array = response.getJSONArray(SharedPref.key_data_details);
                        for (int i = 0;i<array.length();i++){
                            JSONObject object = array.getJSONObject(i);
                            MyReview myReview = new Gson().fromJson(object.toString(),MyReview.class);
                            reviews.add(myReview);
//                        Toast.makeText(context, myReview.getComment_msg(), Toast.LENGTH_SHORT).show();
                        }
                    }}
                    else {
//                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                    if (adapter.getItemCount()==0){

                        linearLayout.setVisibility(View.VISIBLE);
                    }
                    else {
                        linearLayout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                HandleNetworkError.handlerError(error, context);
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

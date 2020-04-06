package com.whoamie.cinetime_nepal.common.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.MovieDetailActivity;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.adapters.MyReviewAdapter;
import com.whoamie.cinetime_nepal.member.models.MyReview;

import java.util.ArrayList;

public class UserReviewFragment extends Fragment {
    ArrayList<MyReview> reviews;
    RecyclerView recyclerView;
    LinearLayout emptyLayout;
    MyReviewAdapter adapter;
    View view;
    public UserReviewFragment(ArrayList<MyReview> reviews) {
        this.reviews=reviews;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_review, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        recyclerView=view.findViewById(R.id.show_user_review_recycler);
        emptyLayout=view.findViewById(R.id.show_review_empty_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter=new MyReviewAdapter(reviews, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Movie movie = reviews.get(position).getMovie();
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
}

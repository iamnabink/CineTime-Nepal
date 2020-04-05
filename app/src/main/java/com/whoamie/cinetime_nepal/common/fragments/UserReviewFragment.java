package com.whoamie.cinetime_nepal.common.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.member.models.MyReview;

import java.util.ArrayList;

public class UserReviewFragment extends Fragment {
    ArrayList<MyReview> reviews = new ArrayList<>();
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
        initVars();
        return view;
    }

    private void initVars() {

    }

    private void initViews() {

    }
}

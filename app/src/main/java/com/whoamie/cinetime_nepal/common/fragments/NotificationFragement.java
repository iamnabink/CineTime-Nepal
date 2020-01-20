package com.whoamie.cinetime_nepal.common.fragments;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.adapter.NotificationAdapter;

public class NotificationFragement extends Fragment {
    View view;
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        initVar();
        initViews();
        loadData();
        return view;
    }

    private void loadData() {
    }

    private void initViews() {

    }

    private void initVar() {
        recyclerView=view.findViewById(R.id.notification_recycler_view);
    }
}

package com.example.cinetime_nepal.common.fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.adapter.NotificationAdapter;

public class NotificationFragement extends Fragment {
    View view;
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);
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

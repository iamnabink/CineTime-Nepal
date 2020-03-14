package com.whoamie.cinetime_nepal.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.YoutubePlayerView;
import com.whoamie.cinetime_nepal.common.adapter.ClipVideosAdapter;
import com.whoamie.cinetime_nepal.common.adapter.TrailerVideosAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Video;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    View view;
    ViewFlipper viewFlipper;
    RecyclerView clipRecyclerV, trailerRecyclerV;
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<Video> videos = new ArrayList<>();
    ClipVideosAdapter clipVideosAdapter;
    TrailerVideosAdapter trailerVideosAdapter;
    TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//        initiateVideoplayer();
        viewFlipper = view.findViewById(R.id.viewFlipper); // get the reference of ViewFlipper
        viewFlipper.startFlipping(); // start the flipping of views
        initViews();
        loadData();
        setUpRecylerView();
        textView = view.findViewById(R.id.test_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), YoutubePlayerView.class));
            }
        });
        return view;
    }

    private void setUpRecylerView() {
        clipRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        clipVideosAdapter=new ClipVideosAdapter(getContext(), images, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {

            }
        });
        clipRecyclerV.setAdapter(clipVideosAdapter);
        trailerRecyclerV.setLayoutManager(new LinearLayoutManager(getContext()));
        trailerVideosAdapter=new TrailerVideosAdapter(getContext(), videos, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {

            }
        });
        trailerRecyclerV.setAdapter(trailerVideosAdapter);
    }

    private void loadData() {
        images.add(R.drawable.thumbnail);
        images.add(R.drawable.thumbnail1);
        images.add(R.drawable.thumbnail);
        images.add(R.drawable.thumbnail1);

        videos.add(new Video(1,"loot 2 Trailer 2020",R.drawable.thumbnail,"dasdasda",1));
        videos.add(new Video(1,"loot 2 Trailer 2020",R.drawable.thumbnail1,"dasdasda",1));
        videos.add(new Video(1,"loot 2 Trailer 2020",R.drawable.thumbnail,"dasdasda",1));
        videos.add(new Video(1,"loot 2 Trailer 2020",R.drawable.thumbnail1,"dasdasda",1));
    }

    private void initViews() {
        clipRecyclerV=view.findViewById(R.id.clip_recyclerv);
        trailerRecyclerV=view.findViewById(R.id.trailer_recyclerv);
//        trailerRecyclerV.setNestedScrollingEnabled(false);
    }
//    private void initiateVideoplayer() {}
}
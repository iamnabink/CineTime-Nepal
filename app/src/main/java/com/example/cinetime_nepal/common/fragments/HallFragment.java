package com.example.cinetime_nepal.common.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.activities.MapNearByCinemasActivity;

public class HallFragment extends Fragment {
    View view;
    ImageView mapNearme;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_hall, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//        mapNearme=view.findViewById(R.id.map_nearme_ic);
//        mapNearme.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), MapNearByCinemasActivity.class));
//            }
//        });
         return view;
    }
}

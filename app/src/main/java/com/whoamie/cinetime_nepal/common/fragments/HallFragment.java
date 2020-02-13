package com.whoamie.cinetime_nepal.common.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.adapter.HallAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Hall;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class HallFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HallAdapter adapter;
    ArrayList<Hall> halls = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hall, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        initUi();
        initRecyclerView();
        loadData();
        return view;
    }

    private void initRecyclerView() {
        adapter=new HallAdapter(halls, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Hall hall = halls.get(position);
                //To-do call api here
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.hall_recyclerview);
    }

    private void loadData() {
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(getContext(), Request.Method.GET, API.getHallDetails, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }


}

package com.whoamie.cinetime_nepal.common.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.adapter.HallAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Hall;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.ProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class HallFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HallAdapter adapter;
    ArrayList<Hall> halls = new ArrayList<>();
    Context context;

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

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
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
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        halls.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.getHallDetails, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray(SharedPref.key_data_details);
                    for (int i = 0; i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        Hall hall = new Gson().fromJson(object.toString(),Hall.class);
                        halls.add(hall);
                        adapter.notifyDataSetChanged();
                        if (adapter.getItemCount() == 0){

                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                System.out.println(error);
                Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
            }
        });
        if (CheckConnectivity.isNetworkAvailable(context)){
            RestClient.getInstance(getContext()).addToRequestQueue(request);
        }
        else {
            Toast.makeText(context, "No network available", Toast.LENGTH_SHORT).show();
        }

    }
}

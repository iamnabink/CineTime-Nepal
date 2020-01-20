package com.whoamie.cinetime_nepal.common.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.adapter.NotificationAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.APIEndPoints;
import com.whoamie.cinetime_nepal.common.models.Notification;
import com.whoamie.cinetime_nepal.common.network.API;

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
        //making request with retrofit 2
//        APIEndPoints apiEndPoints = API.getInstance().create(APIEndPoints.class);
//        Call<Notification> call = apiEndPoints.getNotifications();
//        call.enqueue(new Callback<Notification>() {
//            @Override
//            public void onResponse(Call<Notification> call, Response<Notification> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getContext(), "server responding", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else if(response.body() != null) {
//                    String successmsg,sessionkey;
//                    Notification data = response.body();
//                    Boolean isSuccess = true;
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Notification> call, Throwable t) {
//                Toast.makeText(getContext(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initViews() {

    }

    private void initVar() {
        recyclerView=view.findViewById(R.id.notification_recycler_view);
    }
}

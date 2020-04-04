package com.whoamie.cinetime_nepal.common.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.HomeActivity;
import com.whoamie.cinetime_nepal.common.activities.MapNearByCinemasActivity;
import com.whoamie.cinetime_nepal.common.activities.MovieDetailActivity;
import com.whoamie.cinetime_nepal.common.adapter.HallAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.interfaces.HallAdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Hall;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
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
    ShimmerFrameLayout shimmerFrameLayout;
    Activity mActivity;
    String halldata;
    private static final int MY_PERMISSIONS_REQUEST_OPEN_LOCATION = 738;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hall, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        initUi();
        initRecyclerView();
        loadData();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.hall_location).setVisible(true);
//        MenuItem menuItem = menu.findItem(R.id.hall_location);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.hall_location) {
            checkLocationpermission();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    private void initRecyclerView() {
        adapter = new HallAdapter(halls, getContext(), new HallAdapterClickListener() {
            @Override
            public void callBtnClick(int position, View view) {
                Hall hall = halls.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + hall.getContact()));
                startActivity(intent);
            }

            @Override
            public void visitBtnClick(int position, View view) {
                Hall hall = halls.get(position);
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(hall.getSite_url()));
                startActivity(viewIntent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.hall_recyclerview);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.stopShimmer();
    }

    private void loadData() {
        shimmerFrameLayout.startShimmer();
        halls.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.getHallDetails, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Activity activity = getActivity();
                if (activity != null && isAdded()){
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray(SharedPref.key_data_details);
                    halldata = new Gson().toJson(jsonArray);
//                    System.out.println("data ||||||||||||||||||||||||||------------------------------->"+halldata);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Hall hall = new Gson().fromJson(object.toString(), Hall.class);
                        halls.add(hall);
                    }
                    adapter.notifyDataSetChanged();
                    if (adapter.getItemCount() == 0) {

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
//                System.out.println(error);
                HandleNetworkError.handlerError(error, getContext());
            }
        });
        if (CheckConnectivity.isNetworkAvailable(context)) {
            RestClient.getInstance(getContext()).addToRequestQueue(request);
        } else {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            Toast.makeText(context, "No network available", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkLocationpermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_OPEN_LOCATION);
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_OPEN_LOCATION);
            }
        } else {
            getLocation();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_OPEN_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    getLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "Please allow location permission to access this feature", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void getLocation() {  //for map activity
        Intent intent = new Intent(getContext(), MapNearByCinemasActivity.class);
        intent.putExtra("halldata", halldata);
        startActivity(intent);
    }
}

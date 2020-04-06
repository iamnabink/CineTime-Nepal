package com.whoamie.cinetime_nepal.member.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.ProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.adapters.MyFavMovieAdapter;
import com.whoamie.cinetime_nepal.member.models.FavMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyFavMovieFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    MyFavMovieAdapter adapter;
    ArrayList<FavMovie> favMovies = new ArrayList<>();
    Context context;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout  linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        initVars();
        callgetfavMovieApi();
        return view;
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

    private void initVars() {
        linearLayout=view.findViewById(R.id.fav_empty_layout);
        recyclerView = view.findViewById(R.id.movie_user_recycler);
        shimmerFrameLayout = view.findViewById(R.id.favmovie_shimmer_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapter = new MyFavMovieAdapter(favMovies, getContext(), new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                FavMovie favMovie = favMovies.get(position);
                showDialog(favMovie.getMovie().getId());
            }
        });
        recyclerView.setAdapter(adapter);


    }

    private void showDialog(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Remove this movie from favourite movie list?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callRemoveFavMovieApi(id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void callRemoveFavMovieApi(int id) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.show();
        JSONObject object = new JSONObject();
        try {
            object.put("movie_id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AuthenticatedJSONRequest jsonRequest = new AuthenticatedJSONRequest(getContext(), Request.Method.POST, API.removefavMovie, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.cancel();
                callgetfavMovieApi();
                Toast.makeText(getContext(), "removed successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();

                HandleNetworkError.handlerError(error,getContext());
            }
        });
        if (CheckConnectivity.isNetworkAvailable(context)) {
            RestClient.getInstance(context).addToRequestQueue(jsonRequest);
        } else {
            dialog.cancel();
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    private void callgetfavMovieApi() {
        favMovies.clear();
        shimmerFrameLayout.startShimmer();
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(getContext(), Request.Method.POST, API.getfavMovieDetail, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    if (response.getBoolean("status")) {
                        JSONArray jsonArray = response.getJSONArray(SharedPref.key_data_details);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            FavMovie favMovie = new Gson().fromJson(object.toString(), FavMovie.class);
                            favMovies.add(favMovie);
                        }
                        adapter.notifyDataSetChanged();
                        if (adapter.getItemCount()==0){

                            linearLayout.setVisibility(View.VISIBLE);
                        }
                        else {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
//                    }
//                    else {
//                        Toast.makeText(getContext(), "Unauthorized access", Toast.LENGTH_SHORT).show();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                HandleNetworkError.handlerError(error, context);
            }
        });
//        RestClient.getInstance(getContext()).addToRequestQueue(request);
        if (CheckConnectivity.isNetworkAvailable(context)) {
            RestClient.getInstance(getContext()).addToRequestQueue(request);
        } else {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No internet Available", Toast.LENGTH_SHORT).show();
        }

    }

}

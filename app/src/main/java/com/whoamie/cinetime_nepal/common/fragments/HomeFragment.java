package com.whoamie.cinetime_nepal.common.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.YoutubePlayerView;
import com.whoamie.cinetime_nepal.common.adapter.ClipVideosAdapter;
import com.whoamie.cinetime_nepal.common.adapter.TrailerVideosAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Video;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    View view;
    ViewFlipper viewFlipper;
    RecyclerView clipRecyclerV, trailerRecyclerV;
    ArrayList<Video> clipVideos = new ArrayList<>();
    ArrayList<Video> videos = new ArrayList<>();
    ArrayList<String> viewFlipperImages = new ArrayList<>();
    ClipVideosAdapter clipVideosAdapter;
    TrailerVideosAdapter trailerVideosAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        initViews();
        loadData();
        setUpRecylerView();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.hall_location).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private void setImageInFlipr(String imgUrl) {
        ImageView image = new ImageView(context);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(imgUrl).into(image);
        viewFlipper.addView(image);
    }

    private void initViews() {
        clipRecyclerV = view.findViewById(R.id.clip_recyclerv);
        trailerRecyclerV = view.findViewById(R.id.trailer_recyclerv);
        viewFlipper = view.findViewById(R.id.viewFlipper); // get the reference of ViewFlipper
        viewFlipper.startFlipping(); // start the flipping of views
        shimmerFrameLayout = view.findViewById(R.id.home_shimmer_layout);
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

    private void setUpRecylerView() {
        clipRecyclerV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        clipVideosAdapter = new ClipVideosAdapter(getContext(), clipVideos, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Video video = clipVideos.get(position);
                String videoId = video.getYoutube_url();
                Intent intent = new Intent(getContext(), YoutubePlayerView.class);
                intent.putExtra("video_id", videoId);
                startActivity(intent);
            }
        });
        clipRecyclerV.setAdapter(clipVideosAdapter);
        trailerRecyclerV.setLayoutManager(new LinearLayoutManager(getContext()));
        trailerVideosAdapter = new TrailerVideosAdapter(getContext(), videos, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Video video = videos.get(position);
                String videoId = video.getYoutube_url();
                Intent intent = new Intent(getContext(), YoutubePlayerView.class);
                intent.putExtra("video_id", videoId);
                startActivity(intent);
            }
        });
        trailerRecyclerV.setAdapter(trailerVideosAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    private void loadData() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API.getVideos, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    if (response.getBoolean("status")) {
                        JSONArray jsonArray = response.getJSONArray(SharedPref.key_data_details);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Video videoTrailer = new Gson().fromJson(object.toString(), Video.class);
                            if (videoTrailer.getType() == 1) {
                                videos.add(videoTrailer);
                                viewFlipperImages.add(videoTrailer.getThumbnail_url());
                            } else if (videoTrailer.getType() == 2) {
                                clipVideos.add(videoTrailer);
                            }
//                             @if($video->type == 1)
//                                Trailer
//                            @elseif($video->type == 2)
//                            Movie Clip
//                                             @else
//                            Movie News
//                            @endif
                        }
                        for (int i = 0; i < viewFlipperImages.size(); i++) {
                            setImageInFlipr(viewFlipperImages.get(i));

                        }
                        clipVideosAdapter.notifyDataSetChanged();
                        trailerVideosAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Something goes wrong", Toast.LENGTH_SHORT).show();
                    }
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
        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions.
        //Volley does retry for you if you have specified the policy.
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (CheckConnectivity.isNetworkAvailable(context)) {
            RestClient.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } else {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            Toast.makeText(context, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
        }
    }
}
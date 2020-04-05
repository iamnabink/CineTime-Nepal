package com.whoamie.cinetime_nepal.common.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.adapters.ProfileFragmentPagerAdapter;
import com.whoamie.cinetime_nepal.member.fragments.MyFavMovieFragment;
import com.whoamie.cinetime_nepal.member.fragments.MyReviewFragment;
import com.whoamie.cinetime_nepal.member.models.FavMovie;
import com.whoamie.cinetime_nepal.member.models.MyReview;
import com.whoamie.cinetime_nepal.member.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowUserProfileFragment extends Fragment {
    int userId;
    View view;
    Context context;
    TextView userName;
    CircleImageView showProfileIv;
    User user;
    ProfileFragmentPagerAdapter pagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<MyReview> reviews = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public ShowUserProfileFragment(int userId) {
        this.userId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_user_profile, container, false);
        callApi();
        initViews();

        loadData();
        return view;
    }

    private void loadData() {
        Picasso.get().load(user.getProfile_pic_url()).placeholder(R.drawable.person_placeholder).error(R.drawable.person_placeholder).into(showProfileIv);

    }

    private void initViews() {
        viewPager = view.findViewById(R.id.view_pager_show_profile);
        tabLayout = view.findViewById(R.id.show_profile_tab_layout);
        fragments.add(new UserFavMovieFragment(movies));
        fragments.add(new UserReviewFragment(reviews));
        showProfileIv=view.findViewById(R.id.show_profile_iv);
        tabTitles.add("FAVOURITE MOVIES");
        tabTitles.add("REVIEWS");
        pagerAdapter = new ProfileFragmentPagerAdapter(getContext(), getChildFragmentManager(), fragments, tabTitles); //setuped pager
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void callApi() {
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API.getUserProfileDetails, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject(SharedPref.key_user_details);
                    user = new Gson().fromJson(object.toString(), User.class);
                    JSONArray userfavMovieArray = object.getJSONArray("fav_movie");
                    for (int i = 0; i<userfavMovieArray.length();i++){
                        JSONObject favMovieObject = userfavMovieArray.getJSONObject(i);
                        FavMovie favMovie = new Gson().fromJson(favMovieObject.toString(),FavMovie.class);
                        Movie movie = favMovie.getMovie();
                        movies.add(movie);
                    }
                    JSONArray userReviewArray = object.getJSONArray("reviews");
                    for (int i = 0;i<userReviewArray.length();i++){
                        JSONObject reviewObject = userReviewArray.getJSONObject(i);
                        MyReview myReview = new Gson().fromJson(reviewObject.toString(),MyReview.class);
                        reviews.add(myReview);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        if (CheckConnectivity.isNetworkAvailable(context)) {
            RestClient.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }
}

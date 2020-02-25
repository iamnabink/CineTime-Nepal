package com.whoamie.cinetime_nepal.member.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.material.tabs.TabLayout;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.HomeActivity;
import com.whoamie.cinetime_nepal.common.activities.SplashScreenActivity;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.activities.EditProfileActivity;
import com.whoamie.cinetime_nepal.member.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.member.adapters.ProfileSectionPager;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    View view;
    TextView logoutIv;
    CircleImageView profileIv;
    CardView editProfileBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProfileSectionPager pagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();
    TextView unameTv, uBio;
    User users;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        intiVar();
        initViews();
        logOut();
        editProfile();
        updateImage();
        setData();
        return view;
    }

    private void updateImage() {
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((HomeActivity) getActivity()), profileIv, ViewCompat.getTransitionName(profileIv));
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void initViews() {
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void intiVar() {
        logoutIv = view.findViewById(R.id.logout_tv);
        editProfileBtn = view.findViewById(R.id.edit_profile_btn);
        profileIv = view.findViewById(R.id.profile_iv);
        unameTv = view.findViewById(R.id.uname_tv);
        uBio = view.findViewById(R.id.u_bio_tv);
        viewPager = view.findViewById(R.id.view_pager_profile);
        tabLayout = view.findViewById(R.id.tab_layout);
        fragments.add(new FavMovieFragement());
        fragments.add(new ProfileReviewFragment());
        tabTitles.add("FAVOURITE MOVIES");
        tabTitles.add("REVIEWS");
        pagerAdapter = new ProfileSectionPager(getContext(), getChildFragmentManager(), fragments, tabTitles);
        pagerAdapter.notifyDataSetChanged();
    }

    private void setData() {
        preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref, Context.MODE_PRIVATE);
        editor = preferences.edit();
        String userString = preferences.getString(SharedPref.key_user_details, "");
        users = new Gson().fromJson(userString, User.class);
        unameTv.setText(users.getName());
        if (users.getName() == null) {
            unameTv.setText("Name");
        }
        uBio.setText(users.getBio());
        if (users.getBio() == null) {
            uBio.setText("Please add your bio");
        }
        Picasso.get()
                .load(users.getProfile_pic_url()).placeholder(R.drawable.no_pp)
                .into(profileIv);
    }

    private void editProfile() {
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });
    }

    private void logOut() {
        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref, Context.MODE_PRIVATE);
                editor = preferences.edit();
//              preferences.edit().clear().commit(); to clear all shared pref
                editor.remove(SharedPref.key_user_details);
                editor.remove(SharedPref.key_user_token);
                editor.apply();
                startActivity(new Intent(getContext(), SplashScreenActivity.class)); //open splash screen
                //back to register fragment change fragment from another fragment
//                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.profile_frag, new RegisterFragment()); //My second Fragment
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }


}
package com.example.cinetime_nepal.member.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.activities.SplashScreenActivity;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.example.cinetime_nepal.member.activities.EditProfileActivity;
import com.example.cinetime_nepal.member.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    View view;
    TextView logoutIv;
    CircleImageView profileIv;
    CardView editProfileBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView unameTv,uBio;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        view.setSystemUiVisibility(uiOptions);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        intiVar();
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
                startActivity(new Intent(getContext(),EditProfileActivity.class));
            }
        });
    }

    private void intiVar() {
        logoutIv = view.findViewById(R.id.logout_tv);
        editProfileBtn = view.findViewById(R.id.edit_profile_btn);
        profileIv = view.findViewById(R.id.profile_iv);
        unameTv= view.findViewById(R.id.uname_tv);
        uBio=view.findViewById(R.id.u_bio_tv);

    }
    private void setData() {
        preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref,Context.MODE_PRIVATE);
        editor = preferences.edit();
        String userString = preferences.getString(SharedPref.key_user_details,"");
        User users = new Gson().fromJson(userString,User.class);
        unameTv.setText(users.getName());
        if (users.getName() == null){
            unameTv.setText("Name");
        }
        uBio.setText(users.getBio());
        if (users.getBio() == null){
            uBio.setText("Please add your bio");
        }
        Picasso.get()
                .load(users.getProfile_pic_url())
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
                //back to register fragment
//                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.profile_frag, new RegisterFragment()); //My second Fragment
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }


}
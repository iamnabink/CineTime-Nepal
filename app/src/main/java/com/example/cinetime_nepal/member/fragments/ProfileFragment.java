package com.example.cinetime_nepal.member.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ReportFragment;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.activities.SplashScreenActivity;
import com.example.cinetime_nepal.common.fragments.RegisterFragment;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.example.cinetime_nepal.member.activities.EditProfileActivity;

public class ProfileFragment extends Fragment {
    View view;
    ImageView logoutIv;
    CardView editProfileBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        intiVar();
        logOut();
        editProfile();
        return view;
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
                SharedPreferences preferences = getContext().getSharedPreferences(SharedPref.key_shared_pref, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
//                preferences.edit().clear().commit(); to clear all shared pref
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

    private void intiVar() {
        logoutIv = view.findViewById(R.id.logout_iv);
        editProfileBtn = view.findViewById(R.id.edit_profile_btn);
    }

}
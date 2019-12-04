package com.example.cinetime_nepal.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.member.activities.SigninActivity;

public class ProfileFragment extends Fragment {
    TextView signupTv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        signupTv=view.findViewById(R.id.signup_tv);
        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SigninActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
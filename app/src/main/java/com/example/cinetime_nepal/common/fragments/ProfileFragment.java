package com.example.cinetime_nepal.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.member.activities.SigninActivity;
import com.example.cinetime_nepal.member.fragments.UProfileFragment;

public class ProfileFragment extends Fragment {
    TextView signupTv;
    Button signinBtn;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_profile, container, false);

        initVar();
        clickListener();
        return view;
    }

    private void initVar() {
        signupTv=view.findViewById(R.id.signup_tv);
        signinBtn=view.findViewById(R.id.signin_btn);
    }

    private void clickListener() {
        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SigninActivity.class);
                startActivity(intent);
            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.profile_f, new UProfileFragment()); //My second Fragment
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
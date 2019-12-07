package com.example.cinetime_nepal.common.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.member.activities.LoginActivity;
import com.example.cinetime_nepal.member.activities.SignUpActivity;

public class RegisterFragment extends Fragment {
    Button actCreateBtn;
    TextView actLogin;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_register, container, false);
         actCreateBtn=view.findViewById(R.id.act_create_btn);
        actLogin=view.findViewById(R.id.create_act_login);
         actCreateBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getContext(), SignUpActivity.class));
             }
         });
         actLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getContext(), LoginActivity.class));
             }
         });
        return view;
    }

}

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
import com.example.cinetime_nepal.common.utils.Validator;
import com.example.cinetime_nepal.member.activities.SigninActivity;
import com.example.cinetime_nepal.member.fragments.UProfileFragment;

public class ProfileFragment extends Fragment {
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

}
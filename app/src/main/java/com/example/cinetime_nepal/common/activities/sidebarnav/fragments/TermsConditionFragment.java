package com.example.cinetime_nepal.common.activities.sidebarnav.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinetime_nepal.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class TermsConditionFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_terms_condition, container, false);
        return root;
    }
}
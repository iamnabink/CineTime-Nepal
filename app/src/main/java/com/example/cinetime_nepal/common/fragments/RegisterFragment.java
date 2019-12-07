package com.example.cinetime_nepal.common.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.cinetime_nepal.R;

public class RegisterFragment extends Fragment {
    Button actCreateBtn;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_register, container, false);
         actCreateBtn=view.findViewById(R.id.act_create_btn);
         actCreateBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });
        return view;
    }

}

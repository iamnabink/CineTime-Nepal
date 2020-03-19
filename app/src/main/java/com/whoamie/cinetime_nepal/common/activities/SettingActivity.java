package com.whoamie.cinetime_nepal.common.activities;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

import com.whoamie.cinetime_nepal.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingActivity extends AppCompatActivity {

    ConstraintLayout expandableView;
    Button arrowBtn;
    CardView cardView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        expandableView = findViewById(R.id.expandableView);
//        arrowBtn = findViewById(R.id.arrowBtn);
//        cardView = findViewById(R.id.cardView);
//
//        arrowBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (expandableView.getVisibility()==View.GONE){
//                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
//                    expandableView.setVisibility(View.VISIBLE);
//                    arrowBtn.setBackgroundResource(R.drawable.ic_arrow_drop_up_black_24dp);
//                } else {
//                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
//                    expandableView.setVisibility(View.GONE);
//                    arrowBtn.setBackgroundResource(R.drawable.ic_arrow_drop_down_black_24dp);
//                }
//            }
//        });
    }
}

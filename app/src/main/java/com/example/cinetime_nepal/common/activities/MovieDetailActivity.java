package com.example.cinetime_nepal.common.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cinetime_nepal.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    TextView showTimetv,reviewTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        initVar();
        displayShowTime();
        makeReview();
    }

    private void makeReview() {
    }

    private void displayShowTime() {
        showTimetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MovieDetailActivity.this,ShowTimeActivity.class));
            }
        });
    }

    private void initVar() {
        showTimetv= findViewById(R.id.d_movie_showtime_tv);
        reviewTv=findViewById(R.id.d_movie_review_tv);
    }
}

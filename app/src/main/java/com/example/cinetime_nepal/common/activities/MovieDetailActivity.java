package com.example.cinetime_nepal.common.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.models.Movie;
import com.example.cinetime_nepal.common.utils.CommentDialog;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    TextView showTimetv, reviewTv, movieNameTv, movieGenreTv, movieSynopsis, movieCastsTv, movieDirectorsTv, releaseDate, movieRuntime, movieLanguage;
    Movie movie;
    ImageView posterImg, bgImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initVar();
        displayShowTime();
        makeReview();
        if (getIntent().getExtras() != null) {
            String movieString = getIntent().getExtras().getString(SharedPref.key_shared_movies_details, "");
            movie = new Gson().fromJson(movieString, Movie.class);
            loadData();
        }

    }

    private void initVar() {
        showTimetv = findViewById(R.id.d_movie_showtime_tv);
        reviewTv = findViewById(R.id.d_movie_review_tv);
        movieNameTv = findViewById(R.id.d_movie_name_tv);
        movieGenreTv = findViewById(R.id.d_movie_genre_tv);
        movieSynopsis = findViewById(R.id.d_movie_synopsis);
        movieCastsTv = findViewById(R.id.d_movie_casts);
        movieDirectorsTv = findViewById(R.id.d_movie_directors);
        releaseDate = findViewById(R.id.d_release_date);
        movieRuntime = findViewById(R.id.d_movie_runtime);
        movieLanguage = findViewById(R.id.d_movie_language);
        posterImg = findViewById(R.id.d_poster_img);
        bgImage = findViewById(R.id.d_bg_image);
    }

    private void loadData() {
        movieNameTv.setText(movie.getName());
        movieGenreTv.setText(movie.getGenre());
        movieSynopsis.setText(movie.getGenre());
        movieCastsTv.setText(movie.getCasts());
        movieDirectorsTv.setText(movie.getDirectors());
        releaseDate.setText(movie.getRelease_date());
        movieRuntime.setText(movie.getRun_time());
        movieLanguage.setText(movie.getLanguage());
        Picasso.get().load(movie.getPoster_url()).into(posterImg);
        Picasso.get().load(movie.getPoster_url()).into(bgImage);
    }

    private void makeReview() {
        reviewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBox();
            }
        });
    }

    private void showDialogBox() {
        CommentDialog dialog = new CommentDialog();
        dialog.show(getSupportFragmentManager(), "Comment Dialog");


    }

    private void displayShowTime() {
        showTimetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movieId = movie.getId();
                startActivity(new Intent(MovieDetailActivity.this, ShowTimeActivity.class));
            }
        });
    }


}

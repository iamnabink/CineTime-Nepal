package com.example.cinetime_nepal.common.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.models.Movie;
import com.example.cinetime_nepal.common.network.API;
import com.example.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.example.cinetime_nepal.common.network.RestClient;
import com.example.cinetime_nepal.common.utils.InternetConnectionCheck;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailActivity extends AppCompatActivity {
    TextView showTimetv, reviewTv, movieNameTv, movieGenreTv, movieSynopsis, movieCastsTv, movieDirectorsTv, releaseDate, movieRuntime, movieLanguage,ratingCount;
    Movie movie;
    ImageView posterImg, bgImage;
    RatingBar ratingBar;
    RecyclerView reviewRecyclerView;


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
        setRecyclerView();

    }

    private void setRecyclerView() {

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
        ratingBar = findViewById(R.id.d_movie_rating_bar);
        ratingCount=findViewById(R.id.d_movie_rating_count);
        reviewRecyclerView=findViewById(R.id.review_recycler_view);

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
        ratingBar.setRating(movie.getRating());
        ratingCount.setText(movie.getRating()==0.0?"N/A":""+movie.getRating()); //elvis operator
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_comment_box, null);
        ratingBar =view.findViewById(R.id.ratingbar);
        builder.setView(view).setTitle("Rate this movie").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("COMMENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callAPI();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void callAPI() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id",movie.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(this, Request.Method.POST, API.getMovieReviews, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieDetailActivity.this, "Server error occurred! Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        if(InternetConnectionCheck.isNetworkAvailable(getApplicationContext())){
            RestClient.getInstance(this).addToRequestQueue(request);
        }
        else {
            Toast.makeText(this, "Can not load data! please connect internet and try again", Toast.LENGTH_SHORT).show();
        }
    }


    private void displayShowTime() {
        showTimetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movieId = movie.getId();
                Intent intent = new Intent(MovieDetailActivity.this, ShowTimeActivity.class);
                intent.putExtra("movie_id", movieId);
                startActivity(intent);
            }
        });
    }

}

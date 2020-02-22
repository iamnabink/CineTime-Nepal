package com.whoamie.cinetime_nepal.common.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.adapter.ReviewAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.ReviewClickListner;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.common.models.Review;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.ProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.member.activities.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailActivity extends AppCompatActivity {
    TextView  movieNameTv, movieGenreTv, movieSynopsis, movieCastsTv, movieDirectorsTv, releaseDate, movieRuntime, movieLanguage,ratingCount;
    EditText messageEt;
    Button reviewTv,showTimetv;
    Movie movie;
    ImageView posterImg, bgImage, emptyRvIv;
    RatingBar ratingBar;
    RecyclerView reviewRecyclerView;
    ProgressDialog dialog;
    CardView movieFavouriteCv,movieTrailerCv;
    ReviewAdapter adapter;
    ArrayList<Review> reviews = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initVar();
        displayShowTime();
        makeReview();
        makeFavouriteMovies();
        if (getIntent().getExtras() != null) {
            String movieString = getIntent().getExtras().getString(SharedPref.key_shared_movies_details, "");
            movie = new Gson().fromJson(movieString, Movie.class);
            loadData();
        }
        setRecyclerView();
        callReviewAPI();


    }

    private void makeFavouriteMovies() {
        movieFavouriteCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetailActivity.this, "Added to favourite movie", Toast.LENGTH_SHORT).show();
                callMakeFavouriteMovieApi();
            }
        });
    }

    private void callMakeFavouriteMovieApi() {

    }

    private void setRecyclerView() {
        adapter= new ReviewAdapter(reviews, getApplicationContext(), new ReviewClickListner() {
            @Override
            public void deleteButtonClick(int position, View view) {
                Review review = reviews.get(position);
                int userId = review.getUser_id();
                int movieId = review.getMovie_id();
//                System.out.println(userId+"  User Id:  "+" Movie Id :  "+movieId);
                deleteReview(userId, movieId);
            }

            @Override
            public void profilePicClick(int position, View view) {
                Review review = reviews.get(position);
                int movieId = review.getMovie_id();
                openUserProfile(movieId);
            }
        });
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviewRecyclerView.setAdapter(adapter);
        if (adapter.getItemCount() == 0){
            emptyRvIv.setVisibility(View.VISIBLE);
        }
    }

    private void openUserProfile(int movieId) {

    }

    private void deleteReview(int userId, int movieId) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id",movieId);
            jsonObject.put("user_id",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(this, Request.Method.POST, API.deleteMovieReview, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    if (response.getBoolean("status")){
                        Toast.makeText(MovieDetailActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        callReviewAPI();
                    }
                    else {
                        Toast.makeText(MovieDetailActivity.this, "Can not delete! An error Occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(MovieDetailActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        if (CheckConnectivity.isNetworkAvailable(getApplicationContext())){
            RestClient.getInstance(getApplicationContext()).addToRequestQueue(request);
        }
        else {
            Toast.makeText(this, "Can not load data! please connect internet and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void initVar() {
        emptyRvIv=findViewById(R.id.empty_review);
        movieFavouriteCv=findViewById(R.id.d_movie_favourite_cv);
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
        dialog=new ProgressDialog(this);
//        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 800);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
        ratingCount.setText(movie.getRating()==0?"N/A":""+movie.getRating()); //elvis operator
    }

    private void makeReview() {
        reviewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences(SharedPref.key_shared_pref,MODE_PRIVATE);
                String token = preferences.getString(SharedPref.key_user_token,null);
                if (token != null){
                    showDialogBox();
                }
                else {
                    Toast.makeText(MovieDetailActivity.this, "Please login to comment", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
    }

    private void showDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_comment_box, null);
        ratingBar =view.findViewById(R.id.ratingbar);
        messageEt= view.findViewById(R.id.comment_et);
        builder.setView(view).setTitle("Rate this movie").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("COMMENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callMakeReviewApi();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }
    private void callMakeReviewApi() {
//        "movie_id":6,
//                "comment_msg":"Awesome Movie",
//                "rating_count":3
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id",movie.getId());
            jsonObject.put("rating_count",ratingBar.getRating());
            jsonObject.put("comment_msg",messageEt.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(getApplicationContext(), Request.Method.POST, API.makeMovieReview, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    if (response.getBoolean("status")){
                        try {
                            Toast.makeText(MovieDetailActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            callReviewAPI();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(MovieDetailActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                HandleNetworkError.handlerError(error,getApplicationContext());
            }
        });
        if (CheckConnectivity.isNetworkAvailable(getApplicationContext())){
            RestClient.getInstance(getApplicationContext()).addToRequestQueue(request);
        }
        else {
            Toast.makeText(this, "Can not load data! please connect internet and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void callReviewAPI() {
        reviews.clear();
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id",movie.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API.getMovieReviews, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    JSONArray dataArray = response.getJSONArray(SharedPref.key_data_details);
                    for(int i = 0;i<dataArray.length();i++){
                        JSONObject reviewObject = dataArray.getJSONObject(i);
                        Review review = new Gson().fromJson(reviewObject.toString(),Review.class);
                        reviews.add(review);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                System.out.println(error);
                Toast.makeText(MovieDetailActivity.this, "Server error occurred! Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        if(CheckConnectivity.isNetworkAvailable(getApplicationContext())){
            RestClient.getInstance(this).addToRequestQueue(request);
        }
        else {
            dialog.dismiss();
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

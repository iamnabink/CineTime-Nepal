package com.whoamie.cinetime_nepal.common.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
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
import com.google.android.material.snackbar.Snackbar;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.adapter.MovieReviewAdapter;
import com.whoamie.cinetime_nepal.common.adapter.MovieFragmentAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.member.interfaces.ReviewClickListner;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.member.models.Review;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CustomProgressDialog;
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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailActivity extends AppCompatActivity {
    TextView movieNameTv, movieGenreTv, movieSynopsis, movieCastsTv, movieDirectorsTv, releaseDate, movieRuntime, movieLanguage, ratingCount;
    EditText messageEt;
    Button reviewTv, showTimetv;
    Movie movie;
    ImageView posterImg, bgImage;
    RatingBar ratingBar;
    RecyclerView reviewRecyclerView, recommendationRecyclerV;
    CustomProgressDialog dialog;
    CardView movieFavouriteCv, movieTrailerCv;
    MovieReviewAdapter adapter;
    MovieFragmentAdapter movieAdapter;
    CoordinatorLayout coordinatorLayout;
    ArrayList<Review> reviews = new ArrayList<>();
    ArrayList<Movie> movies = new ArrayList<>();
    private SlidrInterface slidr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        slidr = Slidr.attach(this);
        initVar();
        if (getIntent().getExtras() != null) {
            String movieString = getIntent().getExtras().getString(SharedPref.key_shared_movies_details, "");
            movie = new Gson().fromJson(movieString, Movie.class);
            loadIntentData();
            if (movie.getStatus() == 1) {
                findViewById(R.id.showing_movie_detail_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.releasing_movie_detail_layout).setVisibility(View.GONE);
                //nowshowing
                makeReviewBtn();
                setRecyclerView();
                makeFavouriteMoviesBtn();
                callReviewAPI();
                showTimeIntent();
            } else if (movie.getStatus() == 0) {
                //upcoming
                findViewById(R.id.showing_movie_detail_layout).setVisibility(View.GONE);
                findViewById(R.id.releasing_movie_detail_layout).setVisibility(View.VISIBLE);
                makeFavouriteMoviesBtn();
                loadReccomendedMovie();
                showReccomendedmovie();
            }
        }

    }

    private void loadReccomendedMovie() {
        JSONObject object = new JSONObject();
        try {
            object.put("genre", movie.getGenre());
            object.put("movie_id", movie.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API.getRecemendedMovie, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("recom_movies");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject movieObject = jsonArray.getJSONObject(i);
                        Movie movie = new Gson().fromJson(movieObject.toString(), Movie.class);
                        movies.add(movie);
                        movieAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        if (CheckConnectivity.isNetworkAvailable(this)) {
            RestClient.getInstance(this).addToRequestQueue(request);
        } else {
            Toast.makeText(this, "No Network Detected", Toast.LENGTH_SHORT).show();
        }
    }


    private void showReccomendedmovie() {
        recommendationRecyclerV.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this, RecyclerView.HORIZONTAL, false));
        movieAdapter = new MovieFragmentAdapter(movies, this, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
//                Toast.makeText(MovieDetailActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                Movie movie = movies.get(position);
                String movieDetails = new Gson().toJson(movie);
                Intent intent = new Intent(MovieDetailActivity.this, MovieDetailActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //to call itself
                intent.putExtra(SharedPref.key_shared_movies_details, movieDetails);
                startActivity(intent);
//                finish();
            }
        });
        recommendationRecyclerV.setAdapter(movieAdapter);
    }

    private void initVar() {
        recommendationRecyclerV = findViewById(R.id.reccom_movies_recycler_view);
        movieFavouriteCv = findViewById(R.id.d_movie_favourite_cv);
        showTimetv = findViewById(R.id.d_movie_showtime_tv);
        reviewTv = findViewById(R.id.d_movie_review_tv);
        movieTrailerCv = findViewById(R.id.d_movie_trailer_cv);
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
        ratingCount = findViewById(R.id.d_movie_rating_count);
        reviewRecyclerView = findViewById(R.id.review_recycler_view);

        dialog = new CustomProgressDialog(this);
//        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 800);
//        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void loadIntentData() {
        movieNameTv.setText(movie.getName());
        movieGenreTv.setText(movie.getGenre());
        movieSynopsis.setText(movie.getSynopsis());
        movieCastsTv.setText(movie.getCasts());
        movieDirectorsTv.setText(movie.getDirectors());
        releaseDate.setText(movie.getRelease_date());
        movieRuntime.setText(movie.getRun_time());
        movieLanguage.setText(movie.getLanguage());
        Picasso.get().load(movie.getPoster_url()).into(posterImg);
        String backgroundImageUrl = "https://img.youtube.com/vi/"+movie.getYoutube_trailer_url()+"/maxresdefault.jpg";
        Picasso.get().load(backgroundImageUrl).into(bgImage);
        ratingBar.setRating(movie.getRating());
        ratingCount.setText(movie.getRating() == 0 ? "N/A" : "" + movie.getRating()); //elvis operator
    }

    private void makeFavouriteMoviesBtn() {
        movieFavouriteCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(SharedPref.key_shared_pref, MODE_PRIVATE);
                String token = preferences.getString(SharedPref.key_user_token, null); //check login or not
                if (token != null) {
                    callMakeFavouriteMovieApi();
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Please login to access this feature", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
        movieTrailerCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoId = movie.getYoutube_trailer_url();
                Intent intent = new Intent(MovieDetailActivity.this, YoutubePlayerView.class);
                intent.putExtra("video_id", videoId);
                startActivity(intent);
            }
        });
    }

    private void callMakeFavouriteMovieApi() {
        final CustomProgressDialog dialog = new CustomProgressDialog(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id", movie.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.show();
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(this, Request.Method.POST, API.makefavMovie, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    if (response.getBoolean("status")) {
                        Snackbar.make(coordinatorLayout, "Added to fav movie", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(coordinatorLayout, "Already Added", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Snackbar.make(coordinatorLayout, error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        if (CheckConnectivity.isNetworkAvailable(this)) {
            RestClient.getInstance(this).addToRequestQueue(request);
        } else {
            Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRecyclerView() {
        adapter = new MovieReviewAdapter(reviews, getApplicationContext(), new ReviewClickListner() {
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
                Intent intent = new Intent(MovieDetailActivity.this, UserProfileActivity.class);
                String id = Integer.toString(review.getUser_id());
                intent.putExtra(SharedPref.key_user_details, id);
                startActivity(intent);

            }
        });
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviewRecyclerView.setAdapter(adapter);
    }

    private void deleteReview(int userId, int movieId) {
        final CustomProgressDialog dialog = new CustomProgressDialog(this);
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id", movieId);
            jsonObject.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(this, Request.Method.POST, API.deleteMovieReview, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    if (response.getBoolean("status")) {
                        Toast.makeText(MovieDetailActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        if (adapter.getItemCount() == 0) {
                            findViewById(R.id.empty_layout_reviews).setVisibility(View.VISIBLE);
                            reviewRecyclerView.setVisibility(View.GONE);
                        } else {
                            findViewById(R.id.empty_layout_reviews).setVisibility(View.GONE);
                            reviewRecyclerView.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
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
                HandleNetworkError.handlerError(error, MovieDetailActivity.this);
            }
        });
        if (CheckConnectivity.isNetworkAvailable(getApplicationContext())) {
            RestClient.getInstance(getApplicationContext()).addToRequestQueue(request);
        } else {
            Toast.makeText(this, "Can not load data! please connect internet and try again", Toast.LENGTH_SHORT).show();
        }
    }


    private void makeReviewBtn() {
        reviewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences(SharedPref.key_shared_pref, MODE_PRIVATE);
                String token = preferences.getString(SharedPref.key_user_token, null);
                if (token != null) {
                    showDialogBox();
                } else {
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
        ratingBar = view.findViewById(R.id.ratingbar);
        messageEt = view.findViewById(R.id.comment_et);
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
        final CustomProgressDialog dialog = new CustomProgressDialog(this);
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id", movie.getId());
            jsonObject.put("rating_count", ratingBar.getRating());
            jsonObject.put("comment_msg", messageEt.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(getApplicationContext(), Request.Method.POST, API.makeMovieReview, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    if (response.getBoolean("status")) {
                        try {
                            Toast.makeText(MovieDetailActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            callReviewAPI();
                            if (adapter.getItemCount() == 0) {
                                findViewById(R.id.empty_layout_reviews).setVisibility(View.VISIBLE);
                                reviewRecyclerView.setVisibility(View.GONE);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
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
                HandleNetworkError.handlerError(error, getApplicationContext());
            }
        });
        if (CheckConnectivity.isNetworkAvailable(getApplicationContext())) {
            RestClient.getInstance(getApplicationContext()).addToRequestQueue(request);
        } else {
            Toast.makeText(this, "Can not load data! please connect internet and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void callReviewAPI() {
        reviews.clear();
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("movie_id", movie.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API.getMovieReviews, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    JSONArray dataArray = response.getJSONArray(SharedPref.key_data_details);
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject reviewObject = dataArray.getJSONObject(i);
                        Review review = new Gson().fromJson(reviewObject.toString(), Review.class);
                        reviews.add(review);
                    }
                    if (adapter.getItemCount() == 0) {
                        findViewById(R.id.empty_layout_reviews).setVisibility(View.VISIBLE);
                        reviewRecyclerView.setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.empty_layout_reviews).setVisibility(View.GONE);
                        reviewRecyclerView.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
//                System.out.println(error);
                HandleNetworkError.handlerError(error, MovieDetailActivity.this);
            }
        });
        if (CheckConnectivity.isNetworkAvailable(getApplicationContext())) {
            RestClient.getInstance(this).addToRequestQueue(request);

        } else {
            dialog.dismiss();
            Toast.makeText(this, "Can not load data! please connect internet and try again", Toast.LENGTH_SHORT).show();
        }
    }


    private void showTimeIntent() {
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

    private void showSnackbarTop() {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Had a snack at Snackbar", Snackbar.LENGTH_LONG);
        View view = snack.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams(); //Main layour in xml
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;

// calculate actionbar height
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

// set margin
        params.setMargins(0, actionBarHeight, 0, 0);

        view.setLayoutParams(params);
        snack.show();
    }

}

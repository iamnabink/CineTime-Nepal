package com.whoamie.cinetime_nepal.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.adapter.MovieActivityAdapter;
import com.whoamie.cinetime_nepal.common.adapter.SearchMovieAdapter;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.ProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowingMovieActivity extends AppCompatActivity {
    Toolbar myToolbar;
    RecyclerView recyclerView, searchRecyclerView;
    MovieActivityAdapter adapter;
    ArrayList<Movie> movies = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    ProgressDialog dialog;
    SearchMovieAdapter searchMovieAdapter;
    View linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_movie);
        myToolbar = findViewById(R.id.showing_movie_toolbar);
        setSupportActionBar(myToolbar);
        initViews();
        loadData();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.activity_showing_recycler);
        linearLayout = findViewById(R.id.showing_movie_search_layout);
        searchRecyclerView = linearLayout.findViewById(R.id.showing_movie_search_recycler_view);
        dialog = new ProgressDialog(this);
        refreshLayout = findViewById(R.id.activity_swipe_refresh_l1);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dialog.show();
                loadData();
                refreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchMovieAdapter = new SearchMovieAdapter(movies, this, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Movie movie = movies.get(position);
                String movieDetails = new Gson().toJson(movie);
                Intent intent = new Intent(ShowingMovieActivity.this, MovieDetailActivity.class);
                intent.putExtra(SharedPref.key_shared_movies_details, movieDetails);
                startActivity(intent);
            }
        });
        searchRecyclerView.setAdapter(searchMovieAdapter);
        adapter = new MovieActivityAdapter(this, movies, new AdapterClickListener() {
            @Override
            public void onClick(int position, View view) {
                Movie movie = movies.get(position);
                String movieDetails = new Gson().toJson(movie);
                Intent intent = new Intent(ShowingMovieActivity.this, MovieDetailActivity.class);
                intent.putExtra(SharedPref.key_shared_movies_details, movieDetails);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        dialog.show();
        movies.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.nowShowingMovieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    JSONObject object = response.getJSONObject("data");
                    JSONArray showingMovieList = object.getJSONArray("movies");
                    for (int i = 0; i < showingMovieList.length(); i++) {
                        JSONObject movieObject = showingMovieList.getJSONObject(i);
                        Movie movie = new Gson().fromJson(movieObject.toString(), Movie.class);
                        movies.add(movie);
                    }
                    adapter.notifyDataSetChanged();
                    searchMovieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                HandleNetworkError.handlerError(error, ShowingMovieActivity.this);
                dialog.dismiss();
            }
        });
        if (CheckConnectivity.isNetworkAvailable(this)) {
//            noInternetView.setVisibility(View.GONE);
            RestClient.getInstance(this).addToRequestQueue(request);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
//            noInternetView.setVisibility(View.VISIBLE);
            dialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.hall_location).setVisible(false);
        final MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                linearLayout.setVisibility(View.VISIBLE);
                return true;
            }
        });
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Movies by Name or Genre");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovieAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMovieAdapter.getFilter().filter(newText);
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    menuItem.collapseActionView();
                    searchView.setQuery("", false);
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
        return true;
    }
}

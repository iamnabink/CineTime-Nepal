package com.whoamie.cinetime_nepal.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.whoamie.cinetime_nepal.R;

public class ShowingMovieActivity extends AppCompatActivity {
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_movie);
        myToolbar = findViewById(R.id.showing_movie_toolbar);
        setSupportActionBar(myToolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.settings).setVisible(false);
        menu.findItem(R.id.hall_location).setVisible(false);
        final MenuItem menuItem =  menu.findItem(R.id.search);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(ShowingMovieActivity.this, "Make search layout visible", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Movies by Name or Genre");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    menuItem.collapseActionView();
                    searchView.setQuery("", false);
                    Toast.makeText(ShowingMovieActivity.this, "Hide layout", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }
}

package com.example.cinetime_nepal.common.activities;

import android.os.Bundle;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.fragments.HallFragment;
import com.example.cinetime_nepal.common.fragments.MovieFragment;
import com.example.cinetime_nepal.common.fragments.NotificationFragement;
import com.example.cinetime_nepal.common.fragments.HomeFragment;
import com.example.cinetime_nepal.common.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFragment(new MovieFragment());
        initVar();
        setUpBottomNavigation();
    }
    private void initVar() {

    }

    private void setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottomnav_home:
                                replaceFragment(new HomeFragment());
                                break;
                            case R.id.bottomnav_profile:
                                replaceFragment(new ProfileFragment());
                                break;
                            case R.id.bottomnav_notification:
                                replaceFragment(new NotificationFragement());
                                break;
                            case R.id.bottomnav_hall:
                                replaceFragment(new HallFragment());
                                break;
                            case R.id.bottomnav_movies:
                                replaceFragment(new MovieFragment());
                                break;
                        }
                        return false;
                    }
                });
    }

    private void replaceFragment(Fragment fragment) {
        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.container, fragment);
        // transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}

package com.example.cinetime_nepal.common.activities;

import android.os.Bundle;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.activities.bottomnav.HallFragment;
import com.example.cinetime_nepal.common.activities.bottomnav.MovieFragment;
import com.example.cinetime_nepal.common.activities.bottomnav.NotificationFragement;
import com.example.cinetime_nepal.common.activities.sidebarnav.fragments.HomeFragment;
import com.example.cinetime_nepal.common.activities.sidebarnav.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initVar();
        setUpSideNavigationDrawer();
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

    private void setUpSideNavigationDrawer() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawer);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { //side navigation bar
        menuItem.setChecked(true);
        drawer.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_my_profile:
                navController.navigate(R.id.fragment_profile);
                break;
            case R.id.nav_terms_condition:
                navController.navigate(R.id.fragment_terms_condition);
                break;
            case R.id.nav_about:
                navController.navigate(R.id.fragment_about);
                break;
            case R.id.nav_cinemas_near_me:
                navController.navigate(R.id.fragment_cinemas_nearme);
                break;
            case R.id.nav_settings:
                navController.navigate(R.id.fragment_settings);
                break;
        }
        return true;
    }
}

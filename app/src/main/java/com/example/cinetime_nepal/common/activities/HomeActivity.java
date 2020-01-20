package com.example.cinetime_nepal.common.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.fragments.HallFragment;
import com.example.cinetime_nepal.common.fragments.MovieFragment;
import com.example.cinetime_nepal.common.fragments.NotificationFragement;
import com.example.cinetime_nepal.common.fragments.HomeFragment;
import com.example.cinetime_nepal.common.fragments.RegisterFragment;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.example.cinetime_nepal.member.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar myToolbar;
    private static final int MY_PERMISSIONS_REQUEST_OPEN_LOCATION = 738;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        replaceFragment(new MovieFragment(),"MovieFragment");

        initVar();
        setUpBottomNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.hall_location) {
            checkLocationpermission();

        }
        else if(id == R.id.settings){
            startActivity(new Intent(getApplicationContext(),MapNearByCinemasActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkLocationpermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_OPEN_LOCATION);
            }
            else {
                ActivityCompat.requestPermissions(HomeActivity.this, new
                        String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_OPEN_LOCATION);
            }
        }
        else {
            startActivity(new Intent(getApplicationContext(),SettingActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_OPEN_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    startActivity(new Intent(getApplicationContext(),SettingActivity.class));
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Please allow location permission to access this feature", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
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
                                replaceFragment(new HomeFragment(),"HomeFragment");
                                break;
                            case R.id.bottomnav_profile:
//                              if (SharedPref.name has  user data go to userprofile fragment else replaceFragment() with log in fragment;)
                                SharedPreferences preferences = getSharedPreferences(SharedPref.key_shared_pref,MODE_PRIVATE);
                                String userToken = preferences.getString(SharedPref.key_user_token,null);
                                String userDetails = preferences.getString(SharedPref.key_user_details,null);
                                if (userToken == null && userDetails == null){
                                    replaceFragment(new RegisterFragment(),"RegisterFragment");
                                }
                                else {
                                    replaceFragment(new ProfileFragment(),"ProfileFragment");
                                }
                                break;
                            case R.id.bottomnav_notification:
                                replaceFragment(new NotificationFragement(),"NotificationFragement");
                                break;
                            case R.id.bottomnav_hall:
                                replaceFragment(new HallFragment(),"HallFragment");
                                break;
                            case R.id.bottomnav_movies:
                                replaceFragment(new MovieFragment(),"MovieFragment");
                                break;
                        }
                        return true; //not false (it will automatically make bottom icon selected
                    }
                });
        bottomNavigationView.setSelectedItemId(R.id.bottomnav_movies);
    }

    private void replaceFragment(Fragment fragment, String s) {
        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.container, fragment, s);
        // transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}

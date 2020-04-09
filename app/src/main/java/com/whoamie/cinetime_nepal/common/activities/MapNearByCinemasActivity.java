package com.whoamie.cinetime_nepal.common.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whoamie.cinetime_nepal.common.models.Hall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class MapNearByCinemasActivity extends FragmentActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    private static final int MY_PERMISSIONS_REQUEST_OPEN_LOCATION = 100;
    Location currentLocation;
    ArrayList<Hall> halls = new ArrayList<>();
    private MarkerOptions options = new MarkerOptions();
    FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;
    Double lat, lon;
    ImageView satalitveIv, defaultIv;
    FrameLayout mCustomMarkerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_nearby_cinemas);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        handleData();
        checkPermssion();
    }

    private void handleData() {
        String data = getIntent().getStringExtra("halldata");
        if (data!=null){
            try {
                JSONObject object = new JSONObject(data);
                JSONArray array = object.getJSONArray("values");
                for (int i = 0; i<array.length();i++){
                    JSONObject valuespair = array.getJSONObject(i);
                    JSONObject hallObject = valuespair.getJSONObject("nameValuePairs");
                    Hall hall = new Gson().fromJson(hallObject.toString(),Hall.class);
                    halls.add(hall);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private void checkPermssion() {
        if (ActivityCompat.checkSelfPermission(MapNearByCinemasActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapNearByCinemasActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_OPEN_LOCATION);
            } else {
                ActivityCompat.requestPermissions(MapNearByCinemasActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_OPEN_LOCATION);
            }
            // reuqest for permission

        } else {
            fetchLastlocation();
            // already permission granted
        }
    }

    private void fetchLastlocation() {
        if (isLocationEnabled()) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        currentLocation = location;
                        lat = currentLocation.getLatitude();
                        lon = currentLocation.getLongitude();
                    }
                    mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
                    mapFragment.getMapAsync(MapNearByCinemasActivity.this);
//                Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "," + currentLocation.getLongitude(), Toast.LENGTH_LONG).show()
                }
            });
        } else {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
//            Toast.makeText(MapNearByCinemasActivity.this,""+lat+lon  , Toast.LENGTH_SHORT).show();

        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_OPEN_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastlocation();
                    // permission was granted, yay! Do the
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Please allow location permission to access this feature", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        satalitveIv = findViewById(R.id.satellite_iv);
        defaultIv = findViewById(R.id.default_iv);
        satalitveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
        defaultIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        mMap = googleMap;
//        mMap.getMapType();
        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
        LatLng latLng = new LatLng(lat, lon); //current location
//        createMarker(27.709828, 85.326693, "Big Movies", "City Centre");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng)); //for current location of user
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        for(int i = 0 ; i < halls.size() ; i++) {
            createMarker(halls.get(i).getLat(), halls.get(i).getLon(), halls.get(i).getName(), halls.get(i).getLocation()).showInfoWindow();
        }
//        Circle circle = mMap.addCircle(new CircleOptions()
//                .center(latLng)
//                .radius(50)
//                .strokeWidth(0)
//                .fillColor(Color.parseColor("#500084d3")));

    }
    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker)));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}

package com.example.cinetime_nepal.common.activities;

import android.os.Bundle;

import com.example.cinetime_nepal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class MapNearByCinemasActivity extends FragmentActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    GoogleMap gMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_nearby_cinemas);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap= googleMap;
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
        LatLng bigMovies = new LatLng(27.711333, 85.326510);
        gMap.addMarker(new MarkerOptions().position(bigMovies).title("Big Movies"));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(bigMovies);
        builder.include(bigMovies);
        Polyline polyline = gMap.addPolyline(polylineOptions);
        LatLngBounds bounds = builder.build();
        gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

    }
}

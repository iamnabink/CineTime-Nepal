package com.example.cinetime_nepal.common.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.adapter.ShowTimePagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ShowTimeActivity extends AppCompatActivity {
    ViewPager viewPager;
    ShowTimePagerAdapter sectionsPagerAdapter;
    TabLayout tabs;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_showtime);
        initVar();
        initViews();
        onClick();
    }

    private void onClick() {
    }

    private void initViews() {
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void initVar() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        sectionsPagerAdapter = new ShowTimePagerAdapter(this, getSupportFragmentManager());
    }
}

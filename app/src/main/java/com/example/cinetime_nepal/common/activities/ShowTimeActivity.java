package com.example.cinetime_nepal.common.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.adapter.ShowTimePagerAdapter;
import com.example.cinetime_nepal.common.fragments.ShowTimeFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ShowTimeActivity extends AppCompatActivity {
    ViewPager viewPager;
    ShowTimePagerAdapter sectionsPagerAdapter;
    TabLayout tabs;
    int movieId;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles =new ArrayList<>();
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_showtime);
        handleIntent();
        initVar();
        initViews();
    }

    private void handleIntent() {
        if (getIntent().getExtras() != null){
            movieId = getIntent().getExtras().getInt("movie_id");
        }
    }

    private void initViews() {
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void initVar() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dayAfterTomorrow = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat namedf = new SimpleDateFormat("EEEE");
        String todayDate = df.format(today);
        String tomorrowDate = df.format(tomorrow);
        String dayAfterTomorrowDate = df.format(dayAfterTomorrow);
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        fragments.add(new ShowTimeFragment(movieId,todayDate));
        fragments.add(new ShowTimeFragment(movieId,tomorrowDate));
        fragments.add(new ShowTimeFragment(movieId,dayAfterTomorrowDate));
        tabTitles.add("Today");
        tabTitles.add("Tomorrow");
        tabTitles.add(namedf.format(dayAfterTomorrow));
        sectionsPagerAdapter = new ShowTimePagerAdapter(this, getSupportFragmentManager(),fragments,tabTitles);

    }
}

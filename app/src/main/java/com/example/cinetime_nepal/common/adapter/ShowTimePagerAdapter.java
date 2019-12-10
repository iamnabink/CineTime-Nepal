package com.example.cinetime_nepal.common.adapter;

import android.content.Context;

import com.example.cinetime_nepal.common.fragments.ShowTimeDayFragment;
import com.example.cinetime_nepal.common.fragments.ShowTimeTodayFragment;
import com.example.cinetime_nepal.common.fragments.ShowTimeTomorrowFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ShowTimePagerAdapter extends FragmentPagerAdapter {
    Context context;
//    private String tabTitles[] = new String[]{"Today", "Tomorrow", "Wednesday"};
    public ShowTimePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new ShowTimeTodayFragment();
                break;
            case 1:
                fragment = new ShowTimeTomorrowFragment();
                break;
            case 2:
                fragment = new ShowTimeDayFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "Today";
        switch (position) {
            case 0:
                return "Today";
            case 1:
                return "Tomorrow";
            case 2:
                return "Day";
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

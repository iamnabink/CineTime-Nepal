package com.example.cinetime_nepal.common.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ShowTimePagerAdapter extends FragmentPagerAdapter {
    Context context;
    public ShowTimePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}

package com.whoamie.cinetime_nepal.member.sectionpager;

import android.content.Context;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProfilePagerAdapter extends FragmentPagerAdapter {
    Context context;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles =new ArrayList<>();
    public ProfilePagerAdapter(Context context, FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> tabTitles) {
        super(fm);
        this.context=context;
        this.fragments=fragments;
        this.tabTitles=tabTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

}

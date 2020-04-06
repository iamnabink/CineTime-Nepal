package com.whoamie.cinetime_nepal.member.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.HomeActivity;
import com.whoamie.cinetime_nepal.common.activities.SettingActivity;
import com.whoamie.cinetime_nepal.common.activities.SplashScreenActivity;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.ProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.activities.EditProfileActivity;
import com.whoamie.cinetime_nepal.member.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.member.adapters.ProfileFragmentPagerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    View view;
    Button logoutIv,settingTv;
    CircleImageView profileIv;
    CardView editProfileBtn;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProfileFragmentPagerAdapter pagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();
    TextView unameTv, uBio, memberDateTv;
    User users;
    Activity activity = getActivity();
    Context context;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        if(context instanceof HomeActivity){
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        intiVar();
        initViews();
        onClickEveent();
        logOut();
        editProfile();
        updateImage();
        setData();
        return view;
    }

    private void onClickEveent() {
        settingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });
    }

    private void updateImage() {
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((HomeActivity) getActivity()), profileIv, ViewCompat.getTransitionName(profileIv));
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
//                startActivity(intent, options.toBundle());
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void intiVar() {
        settingTv=view.findViewById(R.id.settings_tv);
        memberDateTv=view.findViewById(R.id.user_created_time_tv);
        logoutIv = view.findViewById(R.id.logout_tv);
        editProfileBtn = view.findViewById(R.id.edit_profile_btn);
        profileIv = view.findViewById(R.id.profile_iv);
        unameTv = view.findViewById(R.id.uname_tv);
        uBio = view.findViewById(R.id.u_bio_tv);
        viewPager = view.findViewById(R.id.view_pager_profile);
        tabLayout = view.findViewById(R.id.tab_layout);
        fragments.add(new MyFavMovieFragment());
        fragments.add(new MyReviewFragment());
        tabTitles.add("FAVOURITE MOVIES");
        tabTitles.add("REVIEWS");
        pagerAdapter = new ProfileFragmentPagerAdapter(getContext(), getChildFragmentManager(), fragments, tabTitles); //setuped pager
        pagerAdapter.notifyDataSetChanged();

    }

    private void setData() {
        preferences = context.getSharedPreferences(SharedPref.key_shared_pref, MODE_PRIVATE);
        editor = preferences.edit();
        String userString = preferences.getString(SharedPref.key_user_details, "");
        users = new Gson().fromJson(userString, User.class);
        String date = "Joined: "+users.getCreated_at();
        memberDateTv.setText(date);
        unameTv.setText(users.getName());
        if (users.getName() == null) {
            unameTv.setText("Name");
        }
        uBio.setText(users.getBio());
        if (users.getBio() == null) {
            uBio.setText("Please add your bio");
        }
        Picasso.get()
                .load(users.getProfile_pic_url()).placeholder(R.drawable.no_pp)
                .into(profileIv);
    }

    private void editProfile() {
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });
    }

    private void logOut() {
        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutFromServer();
                //back to register fragment change fragment from another fragment
//                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.profile_frag, new RegisterFragment()); //My second Fragment
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context=context;
        super.onAttach(context);
    }

    private void logoutFromServer() {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.show();
        AuthenticatedJSONRequest jsonRequest = new AuthenticatedJSONRequest(getContext(), Request.Method.POST, API.logoutUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Activity activity = getActivity();
                if (activity != null && isAdded()){
                dialog.cancel();
                preferences = context.getSharedPreferences(SharedPref.key_shared_pref, MODE_PRIVATE);
                editor = preferences.edit();
//              preferences.edit().clear().commit(); to clear all shared pref
                editor.remove(SharedPref.key_user_details);
                editor.remove(SharedPref.key_user_token);
                editor.apply();
                LoginManager.getInstance().logOut();
                Toast.makeText(context, "Logged-out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, SplashScreenActivity.class)); //open splash screen
                getActivity().onBackPressed();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
//                System.out.println(error);
                HandleNetworkError.handlerError(error,getContext());
            }
        });
        if (CheckConnectivity.isNetworkAvailable(context)) {
            RestClient.getInstance(context).addToRequestQueue(jsonRequest);
        } else {
            dialog.cancel();
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }


}
package com.whoamie.cinetime_nepal.common.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.fragments.ShowUserProfileFragment;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.fragments.ProfileFragment;
import com.whoamie.cinetime_nepal.member.models.User;

public class UserProfileActivity extends AppCompatActivity {
    int userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if (getIntent().getExtras() != null) {
            String userIdIntent = getIntent().getExtras().getString(SharedPref.key_user_details, "");
            userId = Integer.parseInt(userIdIntent);
            SharedPreferences preferences = getSharedPreferences(SharedPref.key_shared_pref, MODE_PRIVATE);
            String userStringShared = preferences.getString(SharedPref.key_user_details, null);
            if (userStringShared!=null){
                User user = new Gson().fromJson(userStringShared, User.class);
                if (userId == user.getId()) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.profile_fragment_container,new ProfileFragment());
                    transaction.commit();
                } else {
                    attachFragment();
                }
            }
            else {
                attachFragment();
            }

        }

    }
    private void attachFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_fragment_container,new ShowUserProfileFragment(userId));
        transaction.commit();
    }

}

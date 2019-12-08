package com.example.cinetime_nepal.member.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.example.cinetime_nepal.common.utils.CustomDialog;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.example.cinetime_nepal.member.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    TextInputEditText editName,editBio;
    SharedPreferences preferences;
    CircleImageView profileIv;
    SharedPreferences.Editor editor;
    Button editBtn;
    String updateUrl;
    CustomDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        initVar();
        loadData();
        editProfile();
        loadImage();
    }

    private void loadImage() {

    }

    private void loadData() {
        preferences=getSharedPreferences(SharedPref.key_shared_pref,MODE_PRIVATE);
        String userDetails = preferences.getString(SharedPref.key_user_details,"");
        User user = new Gson().fromJson(userDetails,User.class);
        editName.setText(user.getName());
        editBio.setText(user.getBio());
        Picasso.get().load(user.getProfile_pic_url()).placeholder(R.drawable.portrait_zoro).into(profileIv);
    }

    private void initVar() {
        editName = findViewById(R.id.edit_name);
        editBio=findViewById(R.id.edit_bio);
        profileIv = findViewById(R.id.edit_profile_iv);
        editBtn=findViewById(R.id.edit_btn);
    }
    private void editProfile() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void update() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",editName.getText().toString());
            jsonObject.put("bio",editBio.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.show();
        AuthenticatedJSONRequest jsonRequest = new AuthenticatedJSONRequest(this, Request.Method.PUT, updateUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


}

package com.example.cinetime_nepal.common.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.network.API;
import com.example.cinetime_nepal.common.network.AuthenticatedJSONRequest;
import com.example.cinetime_nepal.common.network.RestClient;
import com.example.cinetime_nepal.common.utils.CustomDialog;
import com.example.cinetime_nepal.common.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePwdActivity extends AppCompatActivity {
    EditText oldPwdEt,newPwd,cfmPwd;
    Button changeBtn;
    CustomDialog dialog;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initVar();
        onButtonClick();
    }

    private void onButtonClick() {
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPassword=oldPwdEt.getText().toString();
                        String newPassword = newPwd.getText().toString();
                        String confirmPassword = cfmPwd.getText().toString();
                        if (oldPassword.isEmpty() && newPassword.isEmpty() && confirmPassword.isEmpty()){
                           oldPwdEt.setError("Please enter old password");
                           newPwd.setError("Please enter new password");
                           cfmPwd.setError("Please confirm new password");
                        }
                        else if(!newPassword.equals(confirmPassword)){
                            cfmPwd.setError("confirm password doesn't match");
                        }
                        else {
                            changePassword();
                        }
                    }
                });
            }
        });
    }

    private void initVar() {
        oldPwdEt=findViewById(R.id.old_pwd_et);
        newPwd = findViewById(R.id.new_pwd);
        cfmPwd=findViewById(R.id.cfm_pwd);
        changeBtn=findViewById(R.id.edit_btn);
        dialog = new CustomDialog(getApplicationContext());
        preferences = getSharedPreferences(SharedPref.key_shared_pref,MODE_PRIVATE);
        editor = preferences.edit();
    }
    private void changePassword() {
        JSONObject jsonObject = new JSONObject(); // creating json object
        try {
            jsonObject.put("old_password", oldPwdEt.getText().toString());
            jsonObject.put("new_password", newPwd.getText().toString());
            jsonObject.put("confirm_new_password", cfmPwd.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(getApplicationContext(), Request.Method.POST, API.changePwdUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")){

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RestClient.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}

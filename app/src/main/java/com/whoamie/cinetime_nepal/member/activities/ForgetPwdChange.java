package com.whoamie.cinetime_nepal.member.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPwdChange extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_change);
        if (getIntent().getExtras() != null) {
            email = getIntent().getExtras().getString("email", "");
        }
        changeEmail();
    }
    private void changeEmail() {
        final CustomProgressDialog dialog = new CustomProgressDialog(ForgetPwdChange.this);
        dialog.show();
        final String email = "Nabrajkhadka43@gmail.com";
        final String pwd = "Nabrajkhadka43@gmail.com";
        JSONObject object = new JSONObject();
        try {
            object.put("email",email);
            object.put("new_password",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API.resetPassword, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
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
                dialog.dismiss();
                HandleNetworkError.handlerError(error,ForgetPwdChange.this);
            }
        });
        if (CheckConnectivity.isNetworkAvailable(ForgetPwdChange.this)){
            RestClient.getInstance(ForgetPwdChange.this).addToRequestQueue(jsonObjectRequest);
        }
        else {
            Toast.makeText(this, "No internet connection detected", Toast.LENGTH_SHORT).show();
        }
    }
}

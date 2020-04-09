package com.whoamie.cinetime_nepal.member.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText resetNewEt, resetCnfEt;
    Button submitBtn;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_change);
        if (getIntent().getExtras() != null) {
            email = getIntent().getExtras().getString("email", "");
        }
        initViews();
        onClick();
    }

    private void onClick() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPwd = resetNewEt.getText().toString();
                String confirmNewPwd = resetCnfEt.getText().toString();
                if (!newPwd.isEmpty()) {
                    if (newPwd.equals(confirmNewPwd)) {
                        changeEmail(confirmNewPwd);
                    } else {
                        Toast.makeText(ForgetPwdChange.this, "Confirm password does not matches", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgetPwdChange.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        resetNewEt = findViewById(R.id.reset_chng_new_pwd_et);
        resetCnfEt = findViewById(R.id.reset_chng_cfm_pwd_et);
        submitBtn = findViewById(R.id.reset_chng_pwd_btn);
    }

    private void changeEmail(String confirmNewPwd) {
        final CustomProgressDialog dialog = new CustomProgressDialog(ForgetPwdChange.this);
        dialog.show();
        JSONObject object = new JSONObject();
        try {
            object.put("email", email);
            object.put("new_password", confirmNewPwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API.resetPassword, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    if (response.getBoolean("status")) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                HandleNetworkError.handlerError(error, ForgetPwdChange.this);
            }
        });
        if (CheckConnectivity.isNetworkAvailable(ForgetPwdChange.this)) {
            RestClient.getInstance(ForgetPwdChange.this).addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(this, "No internet connection detected", Toast.LENGTH_SHORT).show();
        }
    }
}

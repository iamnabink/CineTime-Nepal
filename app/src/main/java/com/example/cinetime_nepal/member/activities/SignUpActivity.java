package com.example.cinetime_nepal.member.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.network.API;
import com.example.cinetime_nepal.common.network.RestClient;
import com.example.cinetime_nepal.common.utils.CustomDialog;
import com.example.cinetime_nepal.common.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    TextView signupBackArrow, signupFname, signupEmail, signupPwd, signupCfmPwd;
    Button signupBtn;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initVar();
        onClick();
    }

    private void initVar() {
        signupBackArrow = findViewById(R.id.signup_back_arrow);
        signupFname = findViewById(R.id.signup_fname);
        signupEmail = findViewById(R.id.signup_email);
        signupPwd = findViewById(R.id.signup_pwd);
        signupCfmPwd = findViewById(R.id.signup_cfm_pwd);
        signupBtn = findViewById(R.id.signup_btn);
    }

    private void onClick() {
        signupBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signupFname.getText().toString();
                String email = signupEmail.getText().toString();
                String password = signupPwd.getText().toString();
                String confirmPassword = signupCfmPwd.getText().toString();
                if (name.isEmpty()) {
                    signupFname.setError("Name field found empty");
                } else if (!Validator.isEmailValid(email)) {
                    signupEmail.setError("Enter a valid email address");
                } else if (!password.equals(confirmPassword)) {
                    signupCfmPwd.setError("Confirm Password doesn't match");
                } else {
                    signUp();
                }

            }
        });
    }

    private void signUp() {
        final CustomDialog dialog = new CustomDialog(this);
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", signupFname.getText().toString());
            jsonObject.put("email", signupEmail.getText().toString());
            jsonObject.put("password", signupPwd.getText().toString());
            jsonObject.put("password_confirmation", signupCfmPwd.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API.signupUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) {
                        dialog.cancel();
//                        System.out.println("response------>"+response);
                        Toast.makeText(SignUpActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        dialog.cancel();
//                        System.out.println("errror----------"+response.getString("message"));
                        Toast.makeText(SignUpActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                System.out.println("errrrorr------>"+error);
                dialog.cancel();
                Toast.makeText(SignUpActivity.this, "An error occurred please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        RestClient.getInstance(this).addToRequestQueue(request);

    }


}

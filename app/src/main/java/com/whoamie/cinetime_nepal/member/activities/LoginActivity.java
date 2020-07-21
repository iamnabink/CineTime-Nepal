package com.whoamie.cinetime_nepal.member.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.SplashScreenActivity;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CustomProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.common.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.sentry.core.Sentry;

public class LoginActivity extends AppCompatActivity {
    TextView signupTv, emailEt, pwdEt;
    Button signinBtn, forgetPwdBtn;
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVar();
        clickListener();
    }

    private void initVar() {
        emailEt = findViewById(R.id.email_et);
        pwdEt = findViewById(R.id.pwd_et);
        signupTv = findViewById(R.id.signup_tv);
        signinBtn = findViewById(R.id.signin_btn);
        forgetPwdBtn = findViewById(R.id.forget_pwd_btn);
    }

    private void clickListener() {
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = pwdEt.getText().toString();
                if (!Validator.isEmailValid(email)) {
                    emailEt.setError("Invalid email address");
                } else if (password.isEmpty()) {
                    pwdEt.setError("please enter password");
                } else if (password.length() < 5) {
                    pwdEt.setError("password must be greater than 5 character");
                } else {
                    signIn();
                }
            }
        });
        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//                sendSms();
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
        forgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }
//    public String sendSmsAPI() {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        StrictMode.setThreadPolicy(policy);
//        try {
//            // Construct data
//            String apiKey = "apikey=" + "VYq7tBngtgc-L1tTDzGnWWRHb3Kz1kEfNIGMDwtbER";
//            String message = "&message=" + "This is your message";
//            String sender = "&sender=" + "TSN";
//            String numbers = "&numbers=" + "009779865098775";
//
//            // Send data
//            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
//            String data = apiKey + numbers + message + sender;
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//            conn.getOutputStream().write(data.getBytes("UTF-8"));
//            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            final StringBuffer stringBuffer = new StringBuffer();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                stringBuffer.append(line);
//            }
//            rd.close();
//            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
//            return stringBuffer.toString();
//        } catch (Exception e) {
//            System.out.println("Error SMS "+e);
//            Toast.makeText(this, "Error->"+e, Toast.LENGTH_SHORT).show();
//            return "Error "+e;
//        }
//    }


    private void signIn() {
        final CustomProgressDialog dialog = new CustomProgressDialog(this);
        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 800);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        preferences = getSharedPreferences(SharedPref.key_shared_pref, MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", emailEt.getText().toString());
            jsonObject.put("password", pwdEt.getText().toString());
        } catch (JSONException e) {
            Sentry.captureException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API.loginUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.cancel();
                try {
                    if (response.getBoolean("status")) {
                        System.out.println("response----->" + response);
                        JSONObject dataObject = response.getJSONObject(SharedPref.key_data_details);
                        JSONObject userObject = dataObject.getJSONObject(SharedPref.key_user_details);
                        JSONObject tokernObject = dataObject.getJSONObject(SharedPref.key_user_token);
                        String userDetails = userObject.toString(); //convert JSONObject to string
                        String tokenDetails = tokernObject.toString();
                        editor.putString(SharedPref.key_user_details, userDetails);
                        editor.putString(SharedPref.key_user_token, tokenDetails);
                        editor.apply();
//                        System.out.println("errrrror----------->"+preferences.getAll());
                        //Go to user profile fragment
                        if (getIntent().getStringExtra("code1") != null) {
//                            Toast.makeText(LoginActivity.this, "N", Toast.LENGTH_SHORT).show();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("review", "value");
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        } else if (getIntent().getStringExtra("code2") != null) {
//                            Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("favourite", "value");
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Sentry.captureException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                System.out.println("error------->" + error);
                dialog.cancel();
                if (error.networkResponse.statusCode == 401) {
                    Toast.makeText(LoginActivity.this, "Password or email do not match", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        HandleNetworkError.handlerError(error, LoginActivity.this);
                    }
                    catch (Exception e){
                        Sentry.captureException(e);
                    }
                }

            }
        });
        if (CheckConnectivity.isNetworkAvailable(getApplicationContext())) {
            RestClient.getInstance(this).addToRequestQueue(request);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        }
    }

}

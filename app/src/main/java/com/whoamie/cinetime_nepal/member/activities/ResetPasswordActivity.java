package com.whoamie.cinetime_nepal.member.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.whoamie.cinetime_nepal.common.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class ResetPasswordActivity extends AppCompatActivity {
    Button btnSubmit;
    EditText emailResetEt;
    TextView codeErrorMsgTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        btnSubmit=findViewById(R.id.frgt_pwd_btn_submit);
        emailResetEt=findViewById(R.id.reset_pwd_email_et);
        codeErrorMsgTv=findViewById(R.id.code_error_msg_tv);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailResetEt.getText().toString();
                if (!email.isEmpty()){
                    if (Validator.isEmailValid(email)){
                        callSendEmailApi(email);
                    }
                    else {
                        codeErrorMsgTv.setText("Invalid ! Enter a valid email address");
                        codeErrorMsgTv.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    codeErrorMsgTv.setText("Email is required");
                    codeErrorMsgTv.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void callSendEmailApi(final String email) {
        final CustomProgressDialog dialog = new CustomProgressDialog(ResetPasswordActivity.this);
        dialog.show();
        final int code = new Random().nextInt(999999);
        JSONObject object = new JSONObject();
        try {
            object.put("email",email);
            object.put("code",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API.verifyEmail, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    if (response.getBoolean("status")){
                        codeErrorMsgTv.setText("Provided email address is not registered in our database");
                        Toast.makeText(ResetPasswordActivity.this, "An code has been sent to your email address", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this,VerifyEmailActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("code",code);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        codeErrorMsgTv.setVisibility(View.VISIBLE);
                        Toast.makeText(ResetPasswordActivity.this, "Provided email does not exist in database", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                HandleNetworkError.handlerError(error,ResetPasswordActivity.this);
            }
        });
        if (CheckConnectivity.isNetworkAvailable(ResetPasswordActivity.this)){
            RestClient.getInstance(ResetPasswordActivity.this).addToRequestQueue(jsonObjectRequest);
        }
        else {
            dialog.dismiss();
            codeErrorMsgTv.setVisibility(View.GONE);
            Toast.makeText(this, "No internet connection detected", Toast.LENGTH_SHORT).show();
        }
    }
}

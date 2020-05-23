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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class VerifyEmailActivity extends AppCompatActivity {
    Button verifybtn, resendBtn;
    EditText resetCodeEt;
    TextView verifyCodeErrorMsgTv;
    String intentEmail;
    int intentCode, editTextCode, receivedCode, codeRand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViews();
        onClick();
        if (getIntent().getExtras() != null) {
            intentEmail = getIntent().getExtras().getString("email", "");
            intentCode = getIntent().getExtras().getInt("code", 0);
        }
    }

    private void onClick() {
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendCode();
            }
        });
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeInput = resetCodeEt.getText().toString();
                if (!codeInput.isEmpty()){
                    verifyEmailCode();
                }
                else {
                    verifyCodeErrorMsgTv.setText("Verification Code is required");
                    verifyCodeErrorMsgTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void verifyEmailCode() {
        editTextCode = Integer.parseInt(resetCodeEt.getText().toString());
        if (intentCode != 0) {
            if (editTextCode == intentCode) {
                Intent intent = new Intent(VerifyEmailActivity.this, ForgetPwdChange.class);
                intent.putExtra("email", intentEmail);
                startActivity(intent);
                finish();
            } else {
                verifyCodeErrorMsgTv.setText("Invalid code");
                verifyCodeErrorMsgTv.setVisibility(View.VISIBLE);
                Toast.makeText(VerifyEmailActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (this.editTextCode == receivedCode) {
                Intent intent = new Intent(VerifyEmailActivity.this, ForgetPwdChange.class);
                intent.putExtra("email", intentEmail);
                startActivity(intent);
                finish();
            } else {
                verifyCodeErrorMsgTv.setText("Invalid code");
                verifyCodeErrorMsgTv.setVisibility(View.VISIBLE);
                Toast.makeText(VerifyEmailActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initViews() {
        verifybtn = findViewById(R.id.verify_code_btn);
        resendBtn = findViewById(R.id.resend_code_btn);
        resetCodeEt = findViewById(R.id.reset_code_et);
        verifyCodeErrorMsgTv = findViewById(R.id.verify_code_error_msg_tv);
    }

    private void resendCode() {
        final CustomProgressDialog dialog = new CustomProgressDialog(VerifyEmailActivity.this);
        dialog.show();
        JSONObject object = new JSONObject();
        codeRand = new Random().nextInt(999999);
        try {
            object.put("email", intentEmail);
            object.put("code", codeRand);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API.verifyEmail, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    intentCode = 0;
                    if (response.getBoolean("status")) {
                        Toast.makeText(VerifyEmailActivity.this, "A new code has been sent, please check your inbox", Toast.LENGTH_SHORT).show();
                        receivedCode = codeRand;
                    } else {
                        Toast.makeText(VerifyEmailActivity.this, "An error occurred, please try again later", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                HandleNetworkError.handlerError(error, VerifyEmailActivity.this);
            }
        });
        if (CheckConnectivity.isNetworkAvailable(this)) {
            RestClient.getInstance(this).addToRequestQueue(jsonObjectRequest);

        } else {
            dialog.dismiss();
            verifyCodeErrorMsgTv.setVisibility(View.GONE);
            Toast.makeText(this, "No internet connection detected", Toast.LENGTH_SHORT).show();
        }
    }
}

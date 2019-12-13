package com.example.cinetime_nepal.common.activities;

import android.os.Bundle;
import com.example.cinetime_nepal.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePwdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

    }
//    private void changePassword() {
//        changeTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                JSONObject jsonObject = new JSONObject(); // creating json object
//                try {
////                    jsonObject.put("old_password", oldPwdEt.getText().toString());
////                    jsonObject.put("new_password", newPwdEt.getText().toString());
////                    jsonObject.put("confirm_new_password", confmPwdET.getText().toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                AuthenticatedJSONRequest request = new AuthenticatedJSONRequest(getApplicationContext(), Request.Method.POST, API.changePwdUrl, jsonObject, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//            }
//        });
//    }
}

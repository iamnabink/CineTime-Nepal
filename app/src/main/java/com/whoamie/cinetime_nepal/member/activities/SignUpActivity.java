package com.whoamie.cinetime_nepal.member.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.FileUtils;
import com.whoamie.cinetime_nepal.common.utils.CustomProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.ImageConverter;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    TextView signupFname, signupEmail, signupPwd, signupCfmPwd;
    Button signupBtn,signupBackArrow;
    CircleImageView signinProfileIv;
    SharedPreferences preferences;
    private static final int IMAGE_PICKER_REQ_CODE = 976;
    private static final int READ_REQ_CODE = 154;
    String profile_pic_url;
    String selectedImagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initVar();
        onClick();
        imageonClick();
        signInButtonClick();
    }

    private void initVar() {
        signupBackArrow = findViewById(R.id.signup_back_arrow);
        signupFname = findViewById(R.id.signup_fname);
        signupEmail = findViewById(R.id.signup_email);
        signupPwd = findViewById(R.id.signup_pwd);
        signupCfmPwd = findViewById(R.id.signup_cfm_pwd);
        signupBtn = findViewById(R.id.signup_btn);
        signinProfileIv = findViewById(R.id.signin_profile_iv);
    }

    private void onClick() {
        signupBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void signInButtonClick() {
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

    private void imageonClick() {
        signinProfileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //if permission not granted ask for new permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_REQ_CODE);
                }
            } else {
                readImage();
            }
        }
    }

    private void readImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICKER_REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readImage();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case IMAGE_PICKER_REQ_CODE:
                    if (resultCode == Activity.RESULT_OK) {
                        //data gives you the image uri.
                        Uri selectedImageUri = data.getData();
                        selectedImagePath = FileUtils.getPath(this,selectedImageUri);
                        System.out.println("Image Path : " + selectedImagePath);
                        signinProfileIv.setImageURI(selectedImageUri);
                        //Converting to bitmap
                        profile_pic_url = ImageConverter.imageConvert(SignUpActivity.this, selectedImageUri);
//                        System.out.println("base 64??->" + profile_pic_url);
                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Log.e(this.getLocalClassName(), "Invalid Image");
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e(this.getLocalClassName(), "Exception in onActivityResult : " + e.getMessage());
        }
    }

//    public String getPath(Uri uri) { //this function will get path
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

    private void signUp() {
        final CustomProgressDialog dialog = new CustomProgressDialog(this);
        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 800);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", signupFname.getText().toString());
            jsonObject.put("email", signupEmail.getText().toString());
            jsonObject.put("password", signupPwd.getText().toString());
            jsonObject.put("password_confirmation", signupCfmPwd.getText().toString());
            if (profile_pic_url != null) {
                jsonObject.put("image", profile_pic_url);
            }

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
//                        System.out.println("error----------"+response.getString("message"));
                        Toast.makeText(SignUpActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        //retrieving server response
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
                HandleNetworkError.handlerError(error, SignUpActivity.this);
            }
        });
        if (CheckConnectivity.isNetworkAvailable(getApplicationContext())){
            RestClient.getInstance(this).addToRequestQueue(request);
        }
        else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        }


    }


}

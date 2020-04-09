package com.whoamie.cinetime_nepal.common.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.SplashScreenActivity;
import com.whoamie.cinetime_nepal.common.network.API;
import com.whoamie.cinetime_nepal.common.network.HandleNetworkError;
import com.whoamie.cinetime_nepal.common.network.RestClient;
import com.whoamie.cinetime_nepal.common.utils.CheckConnectivity;
import com.whoamie.cinetime_nepal.common.utils.CustomProgressDialog;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.activities.LoginActivity;
import com.whoamie.cinetime_nepal.member.activities.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class RegisterFragment extends Fragment {
    Button actCreateBtn, fb_btn;
    TextView actLogin;
    View view;
    LoginButton loginButton;
    ImageView demo_img;
    CallbackManager callbackManager;
    String name, email, id, image_url, demo;
    Context context;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        initViews();
        onClick();
        fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    private void onClick() {
        actCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }

        });
        actLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        tokenTracker.startTracking();
//    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                Toast.makeText(context, "Logged-out", Toast.LENGTH_SHORT).show();
            } else {
                loadUserData(currentAccessToken);
            }

        }
    };

    private void initViews() {
        actCreateBtn = view.findViewById(R.id.act_create_btn);
        fb_btn = view.findViewById(R.id.fb_btn);
        actLogin = view.findViewById(R.id.create_act_login);
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        demo_img = view.findViewById(R.id.demo_img);
        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile")); //log in with custom button
        loginButton.setPermissions(Arrays.asList("email", "public_profile")); //response will be given according to permission made
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(context, "Successfully Logged-in", Toast.LENGTH_SHORT).show();
                //didn't used it because we you want our app to keep up with the current access
                // token and profile, we can implement AccessTokenTracker and ProfileTracker classes.
//                else we can get user data from login callback like:
//                loadUserData(loginResult.getAccessToken());
//                These classes call your code when access token or profile changes happen.
//                Internally they use receivers, so you need to call stopTracking() on an
//                activity or call a fragment's onDestroy() method.
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Login cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadUserData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    name = object.getString("name");
                    email = object.getString("email");
                    id = object.getString("id");
                    image_url = "https://graph.facebook.com/" + id + "/picture?type=large";
//                    demo = "name: " + name + "email: " + email + "id: " + id;
                    callLoginApi(id, name, email, image_url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "name,email,id"); //requested parameters according to permission set
        request.setParameters(bundle);
        request.executeAsync();
    }

    private void callLoginApi(String id, String name, String email, String image_url) {
        final CustomProgressDialog dialog = new CustomProgressDialog(context);
        if(!((Activity) context).isFinishing())
        {
            dialog.show();
        }
        preferences = context.getSharedPreferences(SharedPref.key_shared_pref, MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("facebook_id", id);
            jsonObject.put("email", email);
            jsonObject.put("image_url", image_url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API.loginWithFb, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Activity activity = getActivity();
                if (activity != null && isAdded()){
                    dialog.cancel();
                    try {
                        if (response.getBoolean("status")) {
                            JSONObject dataObject = response.getJSONObject(SharedPref.key_data_details);
                            JSONObject userObject = dataObject.getJSONObject(SharedPref.key_user_details);
                            JSONObject tokernObject = dataObject.getJSONObject(SharedPref.key_user_token);
                            String userDetails = userObject.toString(); //convert JSONObject to string
                            String tokenDetails = tokernObject.toString();
                            editor.putString(SharedPref.key_user_details, userDetails);
                            editor.putString(SharedPref.key_user_token, tokenDetails);
                            editor.apply();
                            startActivity(new Intent(context, SplashScreenActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Activity activity = getActivity();
                if (activity != null && isAdded()){
                    dialog.cancel();
                    HandleNetworkError.handlerError(error, context);
                }


            }
        });
        if (CheckConnectivity.isNetworkAvailable(context)) {
            RestClient.getInstance(context).addToRequestQueue(request);
        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        }
    }
}

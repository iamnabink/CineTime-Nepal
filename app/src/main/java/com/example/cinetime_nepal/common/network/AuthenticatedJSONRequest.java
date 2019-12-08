package com.example.cinetime_nepal.common.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cinetime_nepal.common.utils.SharedPref;
import com.example.cinetime_nepal.member.models.Token;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

    public class AuthenticatedJSONRequest extends JsonObjectRequest {
        String token;
        Context context;
        String TAG=getClass().getName();
        SharedPreferences preferences;
        //Constructor with required arguments to sent request
        public AuthenticatedJSONRequest(Context context, int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener); //Initializing parameters
            this.context = context;
            if(jsonRequest!=null) Log.i(TAG, "payload: "+jsonRequest);
            preferences = context.getSharedPreferences("My Preferences", Context.MODE_PRIVATE); //Initialing shared prefs
            String tokenString = preferences.getString(SharedPref.key_user_token, null); //getting saved token string from shared preferences, null - since it is easier to check condition later on
            Token tokenObj = new Gson().fromJson(tokenString, Token.class); //converts JSON String (stored in shared prefs) to Java tokenObject (creates Token Class)
            this.token = tokenObj.getAccess_token(); //getAccess_token() - returns "Access_token" from tokenObject (exactly from java Token class)
        }
        //Sometimes it is necessary to add extra headers to the HTTP requests, one common case is to add an Authorization header for HTTP Basic Auth.
        // Volley Request class provides a method called getHeaders() which you need to override to add your custom headers if necessary.
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {   //custom put request headers (Overriding default method)
            Map<String, String> headers = new HashMap<>(); //since data has to be sent in Key and value format "Authorization","Bearer"
            headers.put("Authorization", "Bearer " + token); // storing Authorization type in map along with token
            headers.put("Content-Type", "application/json"); // storing Content Type map
            return headers;
            //getHeader(); HTTP request header is the information, in the form of a text record, that a user's mobile sends to
            // a Web server containing the details of what the mobile wants and will accept back from the server.
            // The request header also contains the type, version and capabilities of the mobile
            // that is making the request so that server returns compatible data.
        }
}

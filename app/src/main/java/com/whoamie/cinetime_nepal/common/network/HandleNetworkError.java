package com.whoamie.cinetime_nepal.common.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class HandleNetworkError {
    public static void handlerError(VolleyError error,Context context) {
        try {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                //This indicates that the reuest has either time out or there is no connection
                Toast.makeText(context, "time out or there is no connection", Toast.LENGTH_SHORT).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context, "an Authentication Failure while performing the request", Toast.LENGTH_SHORT).show();
                //Error indicating that there was an Authentication Failure while performing the request
            } else if (error instanceof ServerError) {
                Toast.makeText(context, "server responded with a error response", Toast.LENGTH_SHORT).show();
                //Indicates that the server responded with a error response
            } else if (error instanceof NetworkError) {
                Toast.makeText(context, "network error while performing the request", Toast.LENGTH_SHORT).show();
                //Indicates that there was network error while performing the request
            } else if (error instanceof ParseError) {
                Toast.makeText(context, "network error while performing the request", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "An unknown  error occurred! please try again later", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "An unknown  error occurred! please try again later", Toast.LENGTH_SHORT).show();
        }
    }
}

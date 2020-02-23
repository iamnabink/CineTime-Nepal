package com.whoamie.cinetime_nepal.common.utils;

import android.app.Activity;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {
    public static void showSnackBar(Activity activity, String message){
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}

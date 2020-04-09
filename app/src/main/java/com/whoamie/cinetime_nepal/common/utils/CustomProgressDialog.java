package com.whoamie.cinetime_nepal.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.whoamie.cinetime_nepal.R;

import androidx.annotation.NonNull;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_progress,null);
        setContentView(v);
    }
}

package com.example.cinetime_nepal.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.cinetime_nepal.R;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_progress,null);
        ImageView imageView =  v.findViewById(R.id.progress_logo);
        AnimatedVectorDrawable animatedVectorDrawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animatedVectorDrawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.ic_logo);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageDrawable(animatedVectorDrawable);
        }
        animatedVectorDrawable.start();
        setContentView(v);
    }
}

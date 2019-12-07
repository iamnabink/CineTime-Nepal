package com.example.cinetime_nepal.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.cinetime_nepal.R;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    public CustomDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_progress,null);
        ImageView imageView =  v.findViewById(R.id.progress_logo);
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1200);
        rotate.setInterpolator(new DecelerateInterpolator());
        imageView.startAnimation(rotate);
        setContentView(v);
    }
}

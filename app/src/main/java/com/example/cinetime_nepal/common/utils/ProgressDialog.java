package com.example.cinetime_nepal.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.cinetime_nepal.R;

import androidx.annotation.NonNull;

public class ProgressDialog extends Dialog {
    public ProgressDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_progress,null);
        setContentView(v);
    }
}

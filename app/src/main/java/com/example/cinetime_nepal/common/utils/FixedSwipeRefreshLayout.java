package com.example.cinetime_nepal.common.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FixedSwipeRefreshLayout extends SwipeRefreshLayout {
    private RecyclerView recyclerView;
    public FixedSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public FixedSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean canChildScrollUp() {
        if (recyclerView!=null) {
            return recyclerView.canScrollVertically(-1);
        }
        return false;
    }
}

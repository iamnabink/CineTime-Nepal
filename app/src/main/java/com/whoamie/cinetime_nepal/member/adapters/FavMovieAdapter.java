package com.whoamie.cinetime_nepal.member.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.whoamie.cinetime_nepal.member.models.FavMovie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.MViewHoler> {

    ArrayList<FavMovie> favMovies = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHoler holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MViewHoler extends RecyclerView.ViewHolder{

        public MViewHoler(@NonNull View itemView) {
            super(itemView);
        }
    }
}

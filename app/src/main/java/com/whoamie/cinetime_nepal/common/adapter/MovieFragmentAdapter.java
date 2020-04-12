package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.activities.MovieDetailActivity;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieFragmentAdapter extends RecyclerView.Adapter<MovieFragmentAdapter.MovieViewHolder> {
    ArrayList<Movie> movies;
    Context context;
    AdapterClickListener listener;

    public MovieFragmentAdapter(ArrayList<Movie> movies, Context context, AdapterClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_fragment_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.genreTv.setText(movie.getGenre());
        holder.nameTv.setText(movie.getName());
        Picasso.get().load(movie.getPoster_url())
                .placeholder(R.drawable.no_image_placeholder)
                .into(holder.imgv);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv, genreTv;
        ImageView imgv;
        LinearLayout row_l;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.s_movie_name_tv);
            genreTv = itemView.findViewById(R.id.s_movie_genre_tv);
            row_l = itemView.findViewById(R.id.row_l);
            imgv = itemView.findViewById(R.id.s_movie_imgv);
            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), v);
                }
            });
            if (context instanceof MovieDetailActivity) {
                row_l.getLayoutParams().height = 350; //(int) context.getResources().getDimension(R.dimen.imageview_height)
                row_l.getLayoutParams().width = 250;
                genreTv.setVisibility(View.GONE);
            }
        }

    }
}

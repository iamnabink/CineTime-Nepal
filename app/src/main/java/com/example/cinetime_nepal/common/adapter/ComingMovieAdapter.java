package com.example.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.models.Movie;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComingMovieAdapter extends RecyclerView.Adapter<ComingMovieAdapter.MovieViewHolder> {
    Context context;
    ArrayList<Movie> movies;
    public ComingMovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_movie_coming,parent,false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.imgv.setImageResource(movie.getImg_url());
        holder.genreTv.setText(movie.getGenre());
        holder.nameTv.setText(movie.getName());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv,genreTv;
        ImageView imgv;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.movie_name_tv);
            genreTv=itemView.findViewById(R.id.movie_genre_tv);
            imgv=itemView.findViewById(R.id.movie_imgv);
        }
    }
}

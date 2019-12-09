package com.example.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowingMovieAdapter extends RecyclerView.Adapter<ShowingMovieAdapter.MovieViewHolder> {
    ArrayList<Movie> sMovies;
    Context context;

    public ShowingMovieAdapter(ArrayList<Movie> sMovies, Context context) {
        this.sMovies = sMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_movie_upcoming,parent,false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = sMovies.get(position);
        Picasso.get().load(movie.getPoster_url()).into(holder.imgv);
        holder.genreTv.setText(movie.getGenre());
        holder.nameTv.setText(movie.getName());
    }

    @Override
    public int getItemCount() {
        return sMovies.size();
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

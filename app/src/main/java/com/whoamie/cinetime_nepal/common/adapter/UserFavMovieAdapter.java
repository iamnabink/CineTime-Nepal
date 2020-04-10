package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Movie;
import com.whoamie.cinetime_nepal.member.models.FavMovie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserFavMovieAdapter extends RecyclerView.Adapter<UserFavMovieAdapter.MViewHoler> {

    ArrayList<Movie> favMovies;
    Context context;
    AdapterClickListener listener;

    public UserFavMovieAdapter(ArrayList<Movie> favMovies, Context context, AdapterClickListener listener) {
        this.favMovies = favMovies;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_fav_movie, parent, false);
        return new MViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHoler holder, int position) {
        Movie favMovie = favMovies.get(position);
        holder.name.setText(favMovie.getName());
        holder.genre.setText(favMovie.getGenre());
        Picasso.get().load(favMovie.getPoster_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return favMovies.size();
    }

    public class MViewHoler extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, genre;

        public MViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fav_movie_imgv);
            name = itemView.findViewById(R.id.fav_movie_name_tv);
            genre = itemView.findViewById(R.id.fav_movie_genre_tv);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), v);
                }

            });
        }
    }
}

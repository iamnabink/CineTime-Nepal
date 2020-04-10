package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieHolder> implements Filterable {
    ArrayList<Movie> movies;
    ArrayList<Movie> moviesFull;
    Context context;
    AdapterClickListener listener;
    public SearchMovieAdapter(ArrayList<Movie> movies, Context context, AdapterClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;
        this.moviesFull=movies;
    }

    @NonNull
    @Override
    public SearchMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_search_movie_layout,parent,false);
        SearchMovieHolder holder = new SearchMovieHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieHolder holder, int position) {
        Movie movie = moviesFull.get(position);
        holder.movieNameTv.setText(movie.getName());
        holder.movieGnreTv.setText(movie.getGenre());
        Picasso.get().load(movie.getPoster_url()).placeholder(R.drawable.no_image_placeholder).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return moviesFull.size();
    }

    public class SearchMovieHolder extends RecyclerView.ViewHolder{

        TextView movieNameTv,movieGnreTv;
        ImageView imageView;
        LinearLayout linearLayout;
        public SearchMovieHolder(@NonNull View itemView) {
            super(itemView);
            movieNameTv= itemView.findViewById(R.id.search_movie_name_tv);
            linearLayout=itemView.findViewById(R.id.search_layout_click);
            movieGnreTv= itemView.findViewById(R.id.search_movie_genre_tv);
            imageView= itemView.findViewById(R.id.search_movie_iv);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(),v);
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return filterList;
    }

    private Filter filterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String charString = constraint.toString();
            if (charString.isEmpty()) {
                moviesFull=movies;
            } else {
                ArrayList<Movie> filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();
                //trim and no case sensetive
                for (Movie item : movies) {
                    if (item.getName().toLowerCase().contains(filterPattern) || item.getGenre().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                moviesFull=filteredList;
            }

            FilterResults results = new FilterResults();
            results.values = moviesFull;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            moviesFull=(ArrayList<Movie>) results.values;
            notifyDataSetChanged();
        }
    };

}

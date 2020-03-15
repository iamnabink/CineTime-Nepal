package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClipVideosAdapter extends RecyclerView.Adapter<ClipVideosAdapter.ClipvHolder> {
    Context context;
    ArrayList<String> images;
    AdapterClickListener listener;

    public ClipVideosAdapter(Context context, ArrayList<String> images, AdapterClickListener listener) {
        this.context = context;
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClipvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_clip_imagev,parent,false);
        ClipvHolder holder = new ClipvHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClipvHolder holder, int position) {
        Picasso.get().load(images.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ClipvHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ClipvHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.clip_thumbnail);
        }
    }
}

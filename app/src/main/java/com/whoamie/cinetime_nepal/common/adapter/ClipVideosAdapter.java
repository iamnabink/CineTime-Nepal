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
import com.whoamie.cinetime_nepal.common.models.Video;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ClipVideosAdapter extends RecyclerView.Adapter<ClipVideosAdapter.ClipvHolder> {
    Context context;
    ArrayList<Video> videos;
    AdapterClickListener listener;

    public ClipVideosAdapter(Context context, ArrayList<Video> videos, AdapterClickListener listener) {
        this.context = context;
        this.videos = videos;
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
        Video video = videos.get(position);
        Picasso.get().load(video.getThumbnail_url()).into(holder.imageView);
        holder.clipTitle.setText(video.getTitle());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ClipvHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView clipTitle;
        CardView clickCardV;
        public ClipvHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.clip_thumbnail);
            clipTitle=itemView.findViewById(R.id.clip_title);
            clickCardV=itemView.findViewById(R.id.clip_card_v);
            clickCardV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(),v);
                }
            });
        }
    }
}

package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Video;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailerVideosAdapter extends RecyclerView.Adapter<TrailerVideosAdapter.ViewH> {
    Context context;
    ArrayList<Video> videos;
    AdapterClickListener listener;

    public TrailerVideosAdapter(Context context, ArrayList<Video> videos, AdapterClickListener listener) {
        this.context = context;
        this.videos = videos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_trailer_view,parent,false);
        ViewH viewH = new ViewH(view);
        return viewH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewH holder, int position) {
        Video video = videos.get(position);
        holder.imageView.setImageResource(video.getThumbnail_url());
        holder.title.setText(video.getTitle());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewH extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        public ViewH(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.trailer_iv);
            title=itemView.findViewById(R.id.trailer_title);
        }
    }
}

package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.models.ShowTime;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ShowTimeViewHolder> {
    ArrayList<ShowTime> showTimes;
    Context context;

    public ShowTimeAdapter(ArrayList<ShowTime> showTimes, Context context) {
        this.showTimes = showTimes;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_showtime,parent,false);
        return new ShowTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTimeViewHolder holder, int position) {
        ShowTime showTime = showTimes.get(position);
        holder.hallNameTv.setText(showTime.getHall().getName());
        holder.locationTv.setText(showTime.getHall().getLocation());
        TimeAdapter adapter = new TimeAdapter(showTime.getTimes(),context);
        holder.recyclervTime.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        holder.recyclervTime.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return showTimes.size();
    }

    public class ShowTimeViewHolder extends RecyclerView.ViewHolder{
        CircleImageView hallIv;
        TextView hallNameTv,locationTv;
        RecyclerView recyclervTime;
        public ShowTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            hallIv=itemView.findViewById(R.id.s_hall_iv);
            hallNameTv=itemView.findViewById(R.id.s_hall_name_tv);
            locationTv=itemView.findViewById(R.id.s_location_tv);
            recyclervTime=itemView.findViewById(R.id.time_recycler_v);
        }
    }
}

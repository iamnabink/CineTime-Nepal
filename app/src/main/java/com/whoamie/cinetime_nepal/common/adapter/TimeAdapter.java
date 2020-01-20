package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.models.Time;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder>{
    List<Time> times;
    Context context;

    public TimeAdapter(List<Time> times, Context context) {
        this.times = times;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_time,parent,false);
        TimeHolder holder = new TimeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeHolder holder, int position) {
        Time time = times.get(position);
        holder.timeBtn.setText(time.getTime());
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public class TimeHolder extends RecyclerView.ViewHolder{
        TextView timeBtn;
        public TimeHolder(@NonNull View itemView) {
            super(itemView);
            timeBtn = itemView.findViewById(R.id.time_btn);
        }
    }
}

package com.example.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.models.Notification;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationView> {
    ArrayList<Notification> notifications;
    Context context;
    @NonNull
    @Override
    public NotificationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_notification,parent,false);
        NotificationView notificationView = new NotificationView(view);
        return notificationView;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationView holder, int position) {
        Notification notification = notifications.get(position);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationView extends RecyclerView.ViewHolder {
        TextView notificationTitle,notificationTime;
        CircleImageView notificationIv;
    public NotificationView(@NonNull View itemView) {
        super(itemView);
        notificationIv=itemView.findViewById(R.id.notification_iv);
        notificationTitle=itemView.findViewById(R.id.notification_tv);
        notificationTime=itemView.findViewById(R.id.notif_time_tv);
    }
}
}

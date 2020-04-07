package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.common.interfaces.HallAdapterClickListener;
import com.whoamie.cinetime_nepal.common.models.Hall;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HallAdapter extends RecyclerView.Adapter<HallAdapter.HallVH> {
    ArrayList<Hall> halls;
    Context context;
    HallAdapterClickListener listener;
    public HallAdapter(ArrayList<Hall> halls, Context context, HallAdapterClickListener listener) {
        this.halls = halls;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HallVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_hall,parent,false);
        HallVH holder = new HallVH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HallVH holder, int position) {
        Hall hall = halls.get(position);
        holder.hallNameTv.setText(hall.getName());
        holder.hallLocationTv.setText(hall.getLocation());
        holder.siteUrl.setText(hall.getSite_url());
        holder.phoneTv.setText(hall.getContact());
        Picasso.get().load(hall.getProfile_pic_url()).placeholder(R.drawable.no_image).into(holder.hallIv);
    }

    @Override
    public int getItemCount() {
        return halls.size();
    }

    public class HallVH extends RecyclerView.ViewHolder{

        TextView hallNameTv,hallLocationTv,siteUrl,phoneTv,siteUrlTv,phoneTvClick;
        ImageView hallIv;
        ConstraintLayout expandableView;
        LinearLayout linearLayout;
        Button arrowBtn;
        CardView cardView;
        public HallVH(@NonNull View itemView) {
            super(itemView);
            hallNameTv= itemView.findViewById(R.id.h_name_tv);
            hallLocationTv=itemView.findViewById(R.id.h_location_tv);
            hallIv=itemView.findViewById(R.id.h_iv);
            expandableView = itemView.findViewById(R.id.expandableView);
            arrowBtn = itemView.findViewById(R.id.arrowBtn);
            cardView = itemView.findViewById(R.id.cardView);
            siteUrl = itemView.findViewById(R.id.mailNumber);
            siteUrlTv = itemView.findViewById(R.id.mailDesc);
            phoneTvClick = itemView.findViewById(R.id.phoneDesc);
            phoneTv = itemView.findViewById(R.id.phoneNumber);
            linearLayout = itemView.findViewById(R.id.animate_layout);
            phoneTvClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.callBtnClick(getAdapterPosition(),v);
                }
            });
            phoneTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phoneTvClick.performClick();
                }
            });
            siteUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    siteUrlTv.performClick();
                }
            });
            siteUrlTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.visitBtnClick(getAdapterPosition(),v);
                }
            });
            arrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableView.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(linearLayout, new AutoTransition());
                        expandableView.setVisibility(View.VISIBLE);
                        arrowBtn.setBackgroundResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    } else {
                        TransitionManager.beginDelayedTransition(linearLayout, new AutoTransition().setDuration(300)); //new AutoTransition().setDuration(3000)
                        arrowBtn.setBackgroundResource(R.drawable.ic_arrow_drop_down_black_24dp);
                        expandableView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}

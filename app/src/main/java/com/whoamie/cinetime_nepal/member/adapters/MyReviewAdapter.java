package com.whoamie.cinetime_nepal.member.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.interfaces.AdapterClickListener;
import com.whoamie.cinetime_nepal.member.interfaces.ReviewClickListner;
import com.whoamie.cinetime_nepal.member.models.MyReview;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ReviewHolder> {
    ArrayList<MyReview> reviews;
    Context context;
    AdapterClickListener listener;

    public MyReviewAdapter(ArrayList<MyReview> reviews, Context context, AdapterClickListener listener) {
        this.reviews = reviews;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_my_review_layout, parent, false);
        ReviewHolder holders = new ReviewHolder(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        MyReview review = reviews.get(position);
        holder.review_movie_name_tv.setText(review.getMovie().getName());
        Picasso.get().load(review.getMovie().getPoster_url()).placeholder(R.drawable.person_placeholder).into(holder.my_review_iv);
        holder.review_msg_tv.setText(review.getComment_msg());
        holder.review_cmnt_tm_tv.setText(review.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        TextView review_movie_name_tv,review_movie_rating_v, review_msg_tv,review_cmnt_tm_tv;
        ImageView my_review_iv,star_iv;
        RelativeLayout relativeLayout;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            review_movie_rating_v = itemView.findViewById(R.id.review_movie_rating_v);
            review_movie_name_tv = itemView.findViewById(R.id.review_movie_name_tv);
            relativeLayout=itemView.findViewById(R.id.review_lng_click_layout);
            review_msg_tv = itemView.findViewById(R.id.review_msg_tv);
            star_iv = itemView.findViewById(R.id.star_iv);
            review_cmnt_tm_tv = itemView.findViewById(R.id.review_cmnt_tm_tv);
            my_review_iv = itemView.findViewById(R.id.my_review_iv);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(),v);
//                    relativeLayout.setClickable(false);
                }
            });
        }
    }
}

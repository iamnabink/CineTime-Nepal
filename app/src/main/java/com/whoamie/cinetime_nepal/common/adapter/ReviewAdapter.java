package com.whoamie.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.models.Review;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    ArrayList<Review> reviews;
    Context context;

    public ReviewAdapter(ArrayList<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comment,parent,false);
        ReviewHolder holder = new ReviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position){
        Review review = reviews.get(position);
        holder.userRating.setRating(review.getRating_count());
        holder.userNameTv.setText(review.getUser().getName());
        Picasso.get().load(review.getUser().getProfile_pic_url()).placeholder(R.drawable.no_pp).into(holder.uIv);
        holder.commentTv.setText(review.getComment_msg());
        holder.commentTime.setText(review.getComment_time());


    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        CircleImageView uIv;
        RatingBar userRating;
        TextView userNameTv,commentTime, commentTv;
        public ReviewHolder(@NonNull View itemView) {
        super(itemView);
        uIv = itemView.findViewById(R.id.u_iv);
        userRating=itemView.findViewById(R.id.user_rating);
        userNameTv=itemView.findViewById(R.id.user_name_tv);
        commentTv=itemView.findViewById(R.id.comment_tv);
        commentTime= itemView.findViewById(R.id.comment_time);
    }
}
}

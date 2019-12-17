package com.example.cinetime_nepal.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cinetime_nepal.R;
import com.example.cinetime_nepal.common.models.Review;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        Picasso.get().load(review.getUser().getProfile_pic_url()).into(holder.uIv);
        holder.commentTv.setText(review.getComment_msg());
        holder.commentTime.setText(review.getComment_time());
        //parsing created time to just time yyyy-MM-dd hh:mm:ss
//        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        try {
//            Date dateObj = dateTimeFormat.parse(review.getCreated_at());
//            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
//            holder.commentTime.setText(timeFormat.format(dateObj));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

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
        userRating=itemView.findViewById(R.id.ratingbar);
        userNameTv=itemView.findViewById(R.id.user_name_tv);
        commentTv=itemView.findViewById(R.id.comment_tv);
        commentTime= itemView.findViewById(R.id.comment_time);
    }
}
}

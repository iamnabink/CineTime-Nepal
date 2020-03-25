package com.whoamie.cinetime_nepal.member.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.utils.SharedPref;
import com.whoamie.cinetime_nepal.member.interfaces.ReviewClickListner;
import com.whoamie.cinetime_nepal.member.models.MyReview;
import com.whoamie.cinetime_nepal.member.models.Review;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ReviewHolder> {
    ArrayList<MyReview> reviews;
    Context context;
    ReviewClickListner listener;

    public MyReviewAdapter(ArrayList<MyReview> reviews, Context context, ReviewClickListner listener) {
        this.reviews = reviews;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_user_review_layout, parent, false);
        ReviewHolder holder = new ReviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        MyReview review = reviews.get(position);
        holder.userRating.setRating(review.getRating_count());
        holder.userNameTv.setText(review.getMovie().getName());
        Picasso.get().load(review.getMovie().getPoster_url()).placeholder(R.drawable.no_pp).into(holder.imageView);
        holder.commentTv.setText(review.getComment_msg());
        holder.commentTime.setText(review.getCreated_at());
//        SharedPreferences preferences = context.getSharedPreferences(SharedPref.key_shared_pref,Context.MODE_PRIVATE);
//        String userData = preferences.getString(SharedPref.key_user_details,null);
//        String userIdReview = String.valueOf(review.getUser_id());
//        if (userData != null){
//            try {
//                JSONObject jsonObject = new JSONObject(userData);
//                String userID = jsonObject.getString("id");
//                if (userIdReview.equals(userID)){
//                    holder.delete_btn.setVisibility(View.VISIBLE);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        RatingBar userRating;
        TextView userNameTv, commentTime, commentTv;
        Button delete_btn;
        ImageView imageView;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            userRating = itemView.findViewById(R.id.user_rating);
            userNameTv = itemView.findViewById(R.id.user_name_tv);
            commentTv = itemView.findViewById(R.id.comment_tv);
            commentTime = itemView.findViewById(R.id.comment_time);
            delete_btn=itemView.findViewById(R.id.delete_btn);
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.deleteButtonClick(getAdapterPosition(),v);
                }
            });
        }
    }
}

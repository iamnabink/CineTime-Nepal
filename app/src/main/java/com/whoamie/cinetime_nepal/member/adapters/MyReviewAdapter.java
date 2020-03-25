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
        String imageUrl = "https://cinetimenepal.whoamie.com/uploads/movies/";
        MyReview review = reviews.get(position);
        holder.review_movie_name_tv.setText(review.getMovie().getName());
        Picasso.get().load(imageUrl+review.getMovie().getPoster_url()).placeholder(R.drawable.no_pp).into(holder.my_review_iv);
        holder.review_msg_tv.setText(review.getComment_msg());
        holder.review_cmnt_tm_tv.setText(review.getCreated_at());
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
        TextView review_movie_name_tv,review_movie_rating_v, review_msg_tv,review_cmnt_tm_tv;
        ImageView my_review_iv,review_remove_btn,star_iv;
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
            review_remove_btn=itemView.findViewById(R.id.review_remove_btn);
            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    review_remove_btn.setVisibility(View.VISIBLE);
                    review_movie_rating_v.setVisibility(View.GONE);
                    review_movie_name_tv.setVisibility(View.GONE);
                    review_msg_tv.setVisibility(View.GONE);
                    review_cmnt_tm_tv.setVisibility(View.GONE);
                    star_iv.setVisibility(View.GONE);
                    review_remove_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClick(getAdapterPosition(),v);
                        }
                    });
                    return true;
                }
            });
        }
    }
}

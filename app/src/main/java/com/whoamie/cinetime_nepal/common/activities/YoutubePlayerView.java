package com.whoamie.cinetime_nepal.common.activities;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.whoamie.cinetime_nepal.R;


public class YoutubePlayerView extends YouTubeBaseActivity {

    String api_key = "AIzaSyDS0BBBfxgM086D1atu56tVkRsC9h5M7lU";
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
//    String url = "https://img.youtube.com/vi/cCNPBEwYH34/maxresdefault.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        youtubePlayer();
    }
    private void youtubePlayer() {
        youTubePlayerView=findViewById(R.id.youtube_player);
        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("cCNPBEwYH34");
//                youTubePlayer.release();
                youTubePlayer.play();
                youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
                youTubePlayer.setFullscreen(true);
                youTubePlayer.setShowFullscreenButton(false);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        youTubePlayerView.initialize(api_key,onInitializedListener);
    }

}

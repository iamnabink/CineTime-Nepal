package com.whoamie.cinetime_nepal.common.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.whoamie.cinetime_nepal.R;
import com.whoamie.cinetime_nepal.common.YoutubePlayer.YoutubeConfig;

public class HomeFragment extends Fragment {

    YouTubePlayerFragment playerFragment;
    View view;
    YouTubePlayer.OnInitializedListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        initiateVideoplayer();
        return view;
    }

    private void initiateVideoplayer() {
//        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_frag);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("5KXLCkhhWbM");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        playerFragment.initialize(YoutubeConfig.getApiKey(),listener);
    }

}
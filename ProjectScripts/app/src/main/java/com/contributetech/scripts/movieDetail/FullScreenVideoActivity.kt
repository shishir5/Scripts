package com.contributetech.scripts.movieDetail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.contributetech.scripts.R
import com.contributetech.scripts.application.Config
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class FullScreenVideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    var mVideoUrl:String? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        val intent:Intent = intent
        if(intent.hasExtra("video_url")) {
            mVideoUrl = intent.getStringExtra("video_url")
        }
        var youTubePlayerView = findViewById(R.id.youtube_view) as YouTubePlayerView
        youTubePlayerView.initialize(Config.YOUTUBE_DEV_KEY, this);
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, result:YouTubeInitializationResult) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player:YouTubePlayer, wasRestored:Boolean) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        player.setFullscreen(true)

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(mVideoUrl);
            player.play()
        }
    }

    val playbackEventListener: YouTubePlayer.PlaybackEventListener = object:YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
        }

        override fun onStopped() {
        }

        override fun onPaused() {
        }
    };

    val playerStateChangeListener: YouTubePlayer.PlayerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
        }

        override fun onLoaded(p0: String?) {
            Log.d("check123 loaded", p0)
        }

        override fun onVideoEnded() {
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }

    }
}
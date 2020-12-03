package com.example.exoplayerdemo.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.exoplayerdemo.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.EventLogger

open class PlayerActivity : AppCompatActivity() {

    private var mPlayer: SimpleExoPlayer? = null
    private var trackSelector: DefaultTrackSelector? = null
    private var trackSelectorParameters: DefaultTrackSelector.Parameters? = null
    private var mPlayerView: StyledPlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val playUri = intent.getStringExtra("streamPath")
        mPlayerView = findViewById(R.id.player_view)

        initPlayer(playUri)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPlayer != null) {
            mPlayer!!.release()
        }
    }

    private fun initPlayer(playUri: String?) {

        if (playUri == null){
            Log.d("jiyi", "playUri is null!")
            return
        }

        mPlayer = SimpleExoPlayer.Builder(this).build()
        mPlayerView!!.player = mPlayer

        // Build the media item.
        val firstLocalMediaItem = MediaItem.fromUri(playUri)
        // Set the media item to be played.
        mPlayer!!.addMediaItem(firstLocalMediaItem)
        /* 设置循环模式 */mPlayer!!.repeatMode = Player.REPEAT_MODE_ALL
        /* 注册listener */
        val builder = ParametersBuilder( /* context= */this)
        trackSelectorParameters = builder.build()
        trackSelector = DefaultTrackSelector( /* context= */this)
        trackSelector!!.parameters = trackSelectorParameters!!
        mPlayer!!.addAnalyticsListener(EventLogger(trackSelector))
        // Prepare the player.
        mPlayer!!.prepare()
        // Start the playback.
        mPlayer!!.play()
    }
}
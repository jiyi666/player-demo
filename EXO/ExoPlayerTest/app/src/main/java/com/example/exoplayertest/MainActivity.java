package com.example.exoplayertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.EventLogger;

import java.io.File;

/* 这个分支用于测试DASH流 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SimpleExoPlayer mPlayer;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    protected StyledPlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlayerView = findViewById(R.id.player_view);

        Button start = (Button) findViewById(R.id.start);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        Button queryItem = (Button) findViewById(R.id.queryItem);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        queryItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                initPlayer();
                break;
            case R.id.pause:
                /* 如果是播放状态，就暂停 */
                if (mPlayer.isPlaying() == true){
                    mPlayer.pause();
                    break;
                }
                /* 如果是等待播放状态，就继续播放 */
                if (mPlayer.getPlayWhenReady() == false){
                    mPlayer.setPlayWhenReady(true);
                    break;
                }
                break;
            case R.id.stop:
                mPlayer.stop();
                break;
            case R.id.queryItem:
                Toast.makeText(this, "Item count:" + mPlayer.getMediaItemCount(), Toast.LENGTH_SHORT).show();
                Log.d("jiyi", "Current Item:" + mPlayer.getCurrentMediaItem().mediaId);
                Log.d("jiyi", "Item Index2:" + mPlayer.getMediaItemAt(2).mediaId);
                break;
            default:
                break;
        }
    }

    private void initPlayer(){
        mPlayer = new SimpleExoPlayer.Builder(this).build();
        mPlayerView.setPlayer(mPlayer);
        /* 本地文件存放路径：/sdcard/Android/data/com.example.exoplayertest/files/ */
        Uri testUri = getMediaFileUri(this,"gangganghao.mp4");
        Uri testUri1 = getMediaFileUri(this,"sudi.mp4");
        Log.d("jiyi", "testUri：" + testUri + ", testUri1:" + testUri1);
        // Build the media item.
        MediaItem firstLocalMediaItem = MediaItem.fromUri(testUri);
        MediaItem secondLocalMediaItem = MediaItem.fromUri(testUri1);
        // Set the media item to be played.
        mPlayer.addMediaItem(firstLocalMediaItem);
        mPlayer.addMediaItem(secondLocalMediaItem);
        /* 设置循环模式 */
        mPlayer.setRepeatMode(mPlayer.REPEAT_MODE_ALL);
        /* 注册listener */
        DefaultTrackSelector.ParametersBuilder builder =
                new DefaultTrackSelector.ParametersBuilder(/* context= */ this);
        trackSelectorParameters = builder.build();
        trackSelector = new DefaultTrackSelector(/* context= */ this);
        trackSelector.setParameters(trackSelectorParameters);
        mPlayer.addAnalyticsListener(new EventLogger(trackSelector));
        // Prepare the player.
        mPlayer.prepare();
        // Start the playback.
        mPlayer.play();
    }

    private void releasePlayer(){
        mPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null){
            releasePlayer();
        }

    }

    public static Uri getMediaFileUri(Context context, String fileName) {
        /* 从/storage/emulated/0/Android/data/com.example.exoplayertest/files/下面获取文件 */
        String filePath = context.getExternalFilesDir(null).getPath();
        File file = new File(filePath, fileName);
        Uri uri = null;
        if(file.exists()) {
        uri = Uri.fromFile(file);
        }
        return uri;
    }
}
package com.example.weimin.android;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

public class VideoStart extends Activity {

    private MediaPlayer player;
    static private int currentPosition;  //返回键之后若无static字段 ，视频销毁开始之后从头播放。加上static则为进程 ，视频连续播放
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_start);
        SurfaceView surface = (SurfaceView) findViewById(R.id.video);
        final SurfaceHolder holder = surface.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (player == null) {
                    player = new MediaPlayer();
                    player.reset();
                    try {
                        player.setDataSource("sdcard/2.3gp");
                        player.setDisplay(holder);
                        player.prepareAsync();
                        player.start();
                        player.seekTo(currentPosition);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (player != null) {
                    currentPosition = player.getCurrentPosition();
                    player.stop();
                    player.release();
                    player = null;
                }
            }
        });
//        final Thread thread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//
//            }
//        };
//        thread.start();
//
        final VideoView vv=(VideoView)findViewById(R.id.view);
        vv.setVideoPath("fsf");
        vv.start();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                vv.start();
                vv.setMediaController(new MediaController(VideoStart.this));
            }
        });
    }
}

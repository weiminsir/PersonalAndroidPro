package com.example.weimin.android;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Weimin on 2016/1/6.
 */
public class MusicService extends Service {

    MediaPlayer player;
    Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicController();
    }

    @Override
    public void onCreate() {
        player = new MediaPlayer();
    }

    class MusicController extends Binder implements MusicHelper {
        @Override
        public void start() {
            MusicService.this.start();
        }

        @Override
        public void continueStart() {
            MusicService.this.continueStart();
        }

        @Override
        public void stop() {
            MusicService.this.stop();
        }

        @Override
        public void seekTo(int progress) {
            MusicService.this.seekTo(progress);
        }
    }

    interface MusicHelper {
        void start();
        void continueStart();
        void stop();
        void seekTo(int progress);
    }
    public void start() {
        player.reset();
        try {
            //加载资源  本地和网络都行
            player.setDataSource(Environment.getExternalStorageDirectory().toString() + "/min.mp4");
//            player.prepare();  同步加载
            player.prepareAsync();//网路加载为异步加载
//            player.start();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    player.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void continueStart() {
        player.start();
    }

    public void stop() {
        player.pause();
    }
    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    public void addTimer() {

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int duration = player.getDuration();
                    int currentPosition = player.getCurrentPosition();
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    Message mag = StartMusic.handler.obtainMessage();
                    mag.setData(bundle);
                    StartMusic.handler.sendMessage(mag);
                }
            }, 5, 500);
        }
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();//资源消失
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

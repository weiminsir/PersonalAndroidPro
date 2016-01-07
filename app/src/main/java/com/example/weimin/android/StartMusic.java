package com.example.weimin.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class StartMusic extends Activity implements View.OnClickListener {

    private Button start;
    private Button stop;
    private Button continueStart;
    private Intent intent;
    private MyServiceConnection connection;
    private MusicService.MusicHelper musicHelper;
    static SeekBar seek;
    static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int duratiomn = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            seek.setMax(duratiomn);
            seek.setProgress(currentPosition);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_music);
        start = (Button) findViewById(R.id.start);
        continueStart = (Button) findViewById(R.id.continueStart);
        stop = (Button) findViewById(R.id.stop);
        seek = (SeekBar) findViewById(R.id.sb);
        start.setOnClickListener(this);
        continueStart.setOnClickListener(this);
        stop.setOnClickListener(this);
        intent = new Intent(this, MusicService.class);
        connection = new MyServiceConnection();
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Toast.makeText(StartMusic.this,"进度改变，手指滑动",Toast.LENGTH_LONG);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(StartMusic.this,"手指按下，开始滑动",Toast.LENGTH_LONG);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress=seekBar.getProgress();
                musicHelper.seekTo(progress);

                Toast.makeText(StartMusic.this,"手指拿开，停止滑动",Toast.LENGTH_LONG);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.continueStart:
                continueStart();
                break;
            case R.id.stop:
                stop();
                break;
            case R.id.exit:
                exit();
                break;
        }
    }

    public void start() {
        musicHelper.start();
    }

    public void stop() {
        musicHelper.stop();
    }

    public void continueStart() {
        musicHelper.continueStart();
    }

    public void exit() {
        unbindService(connection);
        stopService(intent);
    }

    class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicHelper = (MusicService.MusicHelper) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


}

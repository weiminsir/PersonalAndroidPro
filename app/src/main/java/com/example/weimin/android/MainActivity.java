package com.example.weimin.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.log);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        BitmapFactory.Options options = new BitmapFactory.Options();
//不为像素生情内存  之获取宽高。
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile("sdcard/1.jpg", options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        Display display = getWindowManager().getDefaultDisplay();
//拿到屏幕宽高，计算缩放比例
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        int scale = 1;
        int scaleWidth = imageWidth / screenWidth;
        int scaleHeight = imageHeight / screenHeight;
        if (scaleWidth >= scaleHeight && scaleWidth >= 1) {
            scale = scaleWidth;
        } else if (scaleHeight >= scaleWidth && scaleHeight >= 1) {
            scale = scaleHeight;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile("sdcard/1.jpg", options);
    }

    public void memory() {
        File path = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        long availAbleBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
            availAbleBlocks = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
            availAbleBlocks = statFs.getAvailableBlocks();
        }
        formarSize(blockSize * totalBlocks);

    }

    public void formarSize(long size) {

    }

}

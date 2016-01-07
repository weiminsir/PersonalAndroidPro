package com.example.weimin.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PictureCopy extends Activity {
    int startX;
    int startY;
    Paint paint;
    Canvas canvas;
    Bitmap bmpCreate;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_copy);
        image = (ImageView) findViewById(R.id.copy);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), 1);
//创建白纸
        bmpCreate = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        paint = new Paint();
        canvas = new Canvas(bmpCreate);
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        image.setImageBitmap(bmpCreate);
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(8);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) motionEvent.getX();
                        startY = (int) motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        canvas.drawLine(startX, startY, x, y, paint);
                        startX = x;
                        startY = y;
                        image.setImageBitmap(bmpCreate);
                        System.out.println(x + ":" + y);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

    }
    public void copy() {
        Bitmap bmpSrc = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + "/3.jpg");
//用原图创建对象  尺寸一样
        Bitmap bmpCopy = Bitmap.createBitmap(bmpSrc.getWidth(), bmpSrc.getHeight(), bmpSrc.getConfig());
//创建画笔对象
        Paint paint = new Paint();
//创建花瓣对象
        Canvas canvas = new Canvas(bmpSrc);
        //开始绘制  原图赋值到白纸上
        Matrix matrix = new Matrix();
        matrix.setTranslate(10, 10);
        matrix.setScale(2, 5);
        matrix.setRotate(45);
        canvas.drawBitmap(bmpSrc, new Matrix(), paint);
        canvas.drawLine(10, 10, 40, 40, paint);
        ImageView imageView = (ImageView) findViewById(R.id.origin);
        imageView.setImageBitmap(bmpCopy);
    }
    public void save() {
        File file = new File("sdcard/3.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmpCreate.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        //SD 就绪广播
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_MEDIA_MOUNTED);
        intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
        sendBroadcast(intent);

    }
}

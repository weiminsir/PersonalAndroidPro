package com.example.weimin.android;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class CustomObserver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_observer);
        getContentResolver().registerContentObserver(Uri.parse("content://com.android.myContentProvider"), true,
                new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);

                System.out.println("自定义内容观察者内容改变了");

            }
        });


    }
}

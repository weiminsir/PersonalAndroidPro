package com.example.weimin.android;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class AcauireMessage extends Activity {

    private Button get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acauire_message);
        get = (Button) findViewById(R.id.sms);
        ContentResolver cs = getContentResolver();
        cs.registerContentObserver(Uri.parse("content://sms"), true,
                new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
                        super.onChange(selfChange);
                        System.out.println("收到短信改变通知后做出回应");
                    }
                });
    }
    public void getMessage() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://sms"), new String[]{"address", "date", "body", "type"}, null, null, null);
        while (cursor.moveToNext()) {
            String address = cursor.getString(0);
            long date = cursor.getLong(1);
            String body = cursor.getString(2);
            int type = cursor.getInt(3);
        }
    }
}

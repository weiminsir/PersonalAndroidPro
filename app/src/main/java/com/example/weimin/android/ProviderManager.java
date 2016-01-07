package com.example.weimin.android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProviderManager extends Activity {

    private Button insert;
    private Button delete;
    private Button update;
    private Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_manager);
        insert = (Button) findViewById(R.id.insert);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        query = (Button) findViewById(R.id.query);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query();
            }
        });


    }

    public void insert() {
        ContentResolver cs = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "weimin");
        values.put("money", "15666");
        cs.insert(Uri.parse("content://com.android.myContentProvider"), values);
    }

    public void delete() {
        ContentResolver cs = getContentResolver();
        cs.delete(Uri.parse("content://com.android.myContentProvider"), "name=?", new String[]{"小智"});

    }

    public void update() {
        ContentResolver cs = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "小智");
        cs.update(Uri.parse("content://com.android.myContentProvider"), values, "name=?", new String[]{"大致"});
    }
    public void query() {
        ContentResolver cs = getContentResolver();
        //内容提供者主机名 ，后面/之后跟表名   可以解析数据 ，URI  本身可以传递数据
        Cursor cursor = cs.query(Uri.parse("content://com.android.myContentProvider"), null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String money = cursor.getString(2);
            System.out.println(name+":"+money);
        }
    }
}

package com.example.weimin.android;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Weimin on 2016/1/7.
 */
public class MyProvider extends ContentProvider {
    MySQLHelper helper;
    SQLiteDatabase db;
    // 创建URI匹配器
   static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    //检测传入与URI匹配器本身是否相等
    static {
        matcher.addURI("content://com.android.myContentProvider", "person", 1);//content://com.android.myContentProvider/person" 返回1
        matcher.addURI("content://com.android.myContentProvider", "teacher", 2);//content://com.android.myContentProvider/person 返回2
        matcher.addURI("content://com.android.myContentProvider", "teacher/#", 3);//content://com.android.myContentProvider/person/4 返回2
        //携带数据4
    }

//    //数据解析新的API
//    ContentUris contentUris = new ContentUris();
//    long id = ContentUris.parseId(m);


    @Override
    public boolean onCreate() {
        helper = new MySQLHelper(getContext());
        db = helper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return db.query("person", strings, s, strings1, null, null, null, null);
    }

    @Override
    public String getType(Uri uri) {

        if (matcher.match(uri) == 1) {
            return "vnd.android.dir/person";//多条数据
        } else if (matcher.match(uri) == 1) {
            return "vnd.android.cursor.item/person";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (matcher.match(uri) == 1) {
            db.insert("person", null, values);
            //可以发送数据改变通知，注册内容观察者的地方可以收到   ；
//所有注册在这个uri上的 内容观察者都可以收到这个通知
            getContext().getContentResolver().notifyChange(uri, null);
        }
        if (matcher.match(uri) == 2) {
            db.insert("teacher", null, values);
            long id = ContentUris.parseId(uri);
            //可以作为该数据库主键来查询
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("sdgsdgfdfg");
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int i = db.delete("person", s, strings);
        return i;
    }

    @Override
    public int update(Uri uri, ContentValues values, String s, String[] strings) {
        int i = db.update("person", values, s, strings);
        return i;
    }
}

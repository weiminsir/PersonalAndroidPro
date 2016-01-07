package com.example.weimin.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Weimin on 2016/1/7.
 */
public class MySQLHelper extends SQLiteOpenHelper {

    public MySQLHelper(Context context) {
        super(context, "person", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库创建之后不会继续创建  但是可以利用版本更新创建

        db.execSQL("create table person(_id integer primary key autoincrement,name char(10))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("create table student(_id integer primary key autoincrement,name char(10))");

    }
}

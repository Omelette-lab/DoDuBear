package com.example.denglu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Db_connect extends SQLiteOpenHelper {
    SQLiteOpenHelper db;
    public Db_connect(@Nullable Context context){
//        创建数据库data
        super(context,"android_text01_data.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表(当创建成功会产生一个对应的.db文件)
        sqLiteDatabase.execSQL("create table user(_id INTEGER PRIMARY KEY AUTOINCREMENT,account VARCHAR(20),password VARCHAR(20),phone VARCHAR(20),flag INT,icon INT)");//用户表
        sqLiteDatabase.execSQL("create table todolist(_id INTEGER PRIMARY KEY AUTOINCREMENT,item_name VARCHAR(20),deadline VARCHAR(20),icon INT)");//任务表
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

}

package com.example.zhuxuekui.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by zhuxuekui on 2015/5/3.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static String CREATE_BOOK = "create table book" +
            "(id integer primary key autoincrement, " +
            "author text, "+
            "price real, "+
            "pages integer, "+
            "name text) ";
    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";
    private Context mcontext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mcontext,"create succeeded",Toast.LENGTH_LONG).show();
    }

    //为了以后数据的完备性，我们需要对每一个版本号赋予它各自改变的内容，在此方法中对当前数据库版本进行判断，然后执行相应改变即可。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists category");
        onCreate(db);
    }
}

package com.example.zhuxuekui.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(MainActivity.this,"BookStore.db",null,3);
        Button button = (Button)findViewById(R.id.create_database);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("name","the da  code");
                values.put("author","dan brown");
                values.put("pages",454);
                values.put("prices",16.96);
                db.insert("Book",null,values);//插入一条数据

                values.clear();
                // 开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values); // 插入第二条数据
            }
        });

        Button updataData = (Button)findViewById(R.id.update_data);
        updataData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("price",10.99);
                db.update("Book",values,"name=?",new String[]{"the da vici code"});
            }
        });

        Button deleteData = (Button)findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book","pages>?",new String[] {"500"});
            }
        });

        Button queryData = (Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToNext())
                {
                    do {
                        //遍历cursor
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    }while(cursor.moveToNext());
                }
                cursor.close();//游标，或者叫做指针，引用都可以。
            }
        });

        Button replaceData = (Button)findViewById(R.id.replace_data);
        replaceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.beginTransaction();//开始事务

                try {
                    db.delete("Book",null,null);
//                    if(true)
//                        throw new NullPointerException();//模拟中途中断，旧数据无法删除，有点像sql里面的回滚机制
                    ContentValues values = new ContentValues();
                    values.put("name", "Game of Thrones");
                    values.put("author", "George Martin");
                    values.put("pages", 720);
                    values.put("price", 20.85);
                    db.insert("Book", null, values);
                    db.setTransactionSuccessful();//事务执行成功
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });
    }
}

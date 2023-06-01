package com.example.the_day_before2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String dbName, @Nullable CursorFactory factory,
                          int dbVersion) {
        super(context, dbName, factory, dbVersion);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCommand = "CREATE TABLE IF NOT EXISTs Users(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title text not null," +
                "date text not null," +
                "diffDay int not null," +
                "type char(50) not null)";
        db.execSQL(sqlCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade 則是如果資料庫結構有改變了就會觸發 onUpgrade
        final String sqlCommand = "DROP TABLE Users";
        db.execSQL(sqlCommand);
    }

}

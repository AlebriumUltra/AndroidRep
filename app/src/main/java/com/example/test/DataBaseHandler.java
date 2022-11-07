package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db";

    public DataBaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USERS_TABLE = "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + "(" + DBContract.UserEntry.COLUMN_NAME_KEY_ID
                + " INTEGER PRIMARY KEY," + DBContract.UserEntry.COLUMN_NAME_LOGIN + " TEXT," + DBContract.UserEntry.COLUMN_NAME_PASS + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());
        db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.UserEntry.TABLE_NAME, DBContract.UserEntry.COLUMN_NAME_LOGIN + "=?", new String[]{user.getLogin()});
        db.close();
    }
}

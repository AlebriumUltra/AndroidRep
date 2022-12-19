package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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
        if(!checkUser(user)) {
            ContentValues values = new ContentValues();
            values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, user.getLogin());
            values.put(DBContract.UserEntry.COLUMN_NAME_PASS, user.getPass());
            db.insert(DBContract.UserEntry.TABLE_NAME, null, values);
        }
        db.close();
    }

    public void deleteUser(String login)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.UserEntry.TABLE_NAME, DBContract.UserEntry.COLUMN_NAME_LOGIN + "=?", new String[]{login});
        db.close();
    }

    public boolean checkUser(User user)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkUserQuery =  "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE login = ? AND pass = ?;";
        Cursor cursor = db.rawQuery(checkUserQuery, new String[]{user.getLogin(), user.getPass()});
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkLogin(User user)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkUserQuery =  "SELECT  * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE login = ?;";
        Cursor cursor = db.rawQuery(checkUserQuery, new String[]{user.getLogin()});
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>();
        String selectQuery = "Select * FROM " + DBContract.UserEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return usersList;
    }


    public void changePassword(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.UserEntry.COLUMN_NAME_PASS, password);
        db.update(DBContract.UserEntry.TABLE_NAME, contentValues, "login=?", new String[]{login});
    }
}

package com.lynch.schoolschedule.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;
import com.lynch.schoolschedule.Entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

    private final SchoolScheduleDbHelper dbHelper;

    public UserHelper(Context context) {
        dbHelper = new SchoolScheduleDbHelper(context);
    }

    public long insertUser(UserEntity user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        return db.insert("users", null, values);
    }

    public UserEntity getUser(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            UserEntity user = new UserEntity(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password"))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public UserEntity getUserByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, "name = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            UserEntity user = new UserEntity(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password"))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                UserEntity user = new UserEntity(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password"))
                );
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return users;
    }

    public int updateUser(UserEntity user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        return db.update("users", values, "id = ?", new String[]{String.valueOf(user.getId())});
    }

    public int deleteUser(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("users", "id = ?", new String[]{String.valueOf(id)});
    }
}

package com.lynch.schoolschedule.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;
import com.lynch.schoolschedule.Entities.TermEntity;

import java.util.ArrayList;
import java.util.List;

public class TermHelper {

    private final SchoolScheduleDbHelper dbHelper;

    public TermHelper(Context context) {
        dbHelper = new SchoolScheduleDbHelper(context);
    }

    public long insertTerm(TermEntity term) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", term.getName());
        values.put("start_date", term.getStartDate());
        values.put("end_date", term.getEndDate());
        return db.insert("terms", null, values);
    }

    public TermEntity getTerm(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("terms", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            TermEntity term = new TermEntity(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("start_date")),
                    cursor.getString(cursor.getColumnIndexOrThrow("end_date"))
            );
            cursor.close();
            return term;
        }
        return null;
    }

    public List<TermEntity> getAllTerms() {
        List<TermEntity> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("terms", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TermEntity term = new TermEntity(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("start_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("end_date"))
                );
                list.add(term);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public int updateTerm(TermEntity term) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", term.getName());
        values.put("start_date", term.getStartDate());
        values.put("end_date", term.getEndDate());
        return db.update("terms", values, "id = ?", new String[]{String.valueOf(term.getId())});
    }

    public int deleteTerm(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("terms", "id = ?", new String[]{String.valueOf(id)});
    }
}

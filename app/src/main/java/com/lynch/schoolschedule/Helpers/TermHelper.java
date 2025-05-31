package com.lynch.schoolschedule.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Assessments.AssessmentFactory;
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
        values.put("name", term.getTermName());
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
    public TermEntity getTermByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM terms WHERE name = ?";
        String[] args = {name};
        Cursor cursor = db.rawQuery(query, args);
        TermEntity term = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String termName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
            String endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
            term = new TermEntity(id, termName, startDate, endDate);
        }
        cursor.close();
        return term;
    }

    public List<TermEntity> searchTerms(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<TermEntity> results = new ArrayList<>();
        String sql = "SELECT * FROM terms WHERE name LIKE ?";
        String[] args = {"%" + query + "%"};
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String termName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
            String endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));

            TermEntity term = new TermEntity(id, termName, startDate, endDate);
            results.add(term);
        }
        cursor.close();
        return results;
    }


    public int updateTerm(TermEntity term) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", term.getTermName());
        values.put("start_date", term.getStartDate());
        values.put("end_date", term.getEndDate());
        return db.update("terms", values, "id = ?", new String[]{String.valueOf(term.getId())});
    }

    public int deleteTerm(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("terms", "id = ?", new String[]{String.valueOf(id)});
    }
}

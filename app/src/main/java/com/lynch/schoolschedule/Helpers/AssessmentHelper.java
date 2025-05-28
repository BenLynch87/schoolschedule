package com.lynch.schoolschedule.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;

import java.util.ArrayList;
import java.util.List;

public class AssessmentHelper {

    private final SchoolScheduleDbHelper dbHelper;

    public AssessmentHelper(Context context) {
        this.dbHelper = new SchoolScheduleDbHelper(context);
    }

    public void insertAssessment(Assessment a) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class_id", a.getClassId());
        values.put("title", a.getTitle());
        values.put("due_date", a.getDueDate());
        values.put("type", a.getType());
        db.insert("assessments", null, values);
    }

    public void updateAssessment(Assessment a) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", a.getTitle());
        values.put("due_date", a.getDueDate());
        values.put("type", a.getType());
        db.update("assessments", values, "id = ?", new String[]{String.valueOf(a.getId())});
    }

    public void deleteAssessment(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("assessments", "id = ?", new String[]{String.valueOf(id)});
    }

    public Assessment getAssessment(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("assessments", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Assessment a = null;
        if (cursor.moveToFirst()) {
            a = new Assessment();
            a.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            a.setClassId(cursor.getInt(cursor.getColumnIndexOrThrow("class_id")));
            a.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            a.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow("due_date")));
            a.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
        }
        cursor.close();
        return a;
    }

    public List<Assessment> getAllAssessments() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Assessment> list = new ArrayList<>();
        Cursor cursor = db.query("assessments", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Assessment a = new Assessment();
            a.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            a.setClassId(cursor.getInt(cursor.getColumnIndexOrThrow("class_id")));
            a.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            a.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow("due_date")));
            a.setType(cursor.getString(cursor.getColumnIndexOrThrow("type")));
            list.add(a);
        }
        cursor.close();
        return list;
    }
}

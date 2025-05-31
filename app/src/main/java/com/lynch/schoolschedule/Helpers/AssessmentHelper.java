package com.lynch.schoolschedule.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Assessments.AssessmentFactory;
import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;

import java.time.LocalDateTime;
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
        db.delete("assessments", "id=?", new String[]{String.valueOf(id)});
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
    public List<Assessment> getAssessmentsDueBetween(LocalDateTime start, LocalDateTime end) {
        List<Assessment> assessments = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM assessments WHERE due_date >= ? AND due_date <= ?";
        String[] args = {start.toString(), end.toString()};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                assessments.add(AssessmentFactory.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return assessments;
    }
    public List<Assessment> getAssessmentsByTermId(int termId) {
        List<Assessment> assessments = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT a.* FROM assessments a " +
                "JOIN classes c ON a.class_id = c.id " +
                "WHERE c.term_id = ?";
        String[] args = {String.valueOf(termId)};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                assessments.add(AssessmentFactory.fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return assessments;
    }
    public List<Assessment> getAllAssessments() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Assessment> list = new ArrayList<>();
        Cursor cursor = db.query("assessments", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(AssessmentFactory.fromCursor(cursor));
        }
        cursor.close();
        return list;
    }
    public List<Assessment> getAssessmentsByClassId(int classId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Assessment> list = new ArrayList<>();
        String selection = "class_id = ?";
        String[] selectionArgs = { String.valueOf(classId) };

        Cursor cursor = db.query("assessments", null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            list.add(AssessmentFactory.fromCursor(cursor));
        }
        cursor.close();
        return list;
    }
    public List<Assessment> searchAssessments(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Assessment> results = new ArrayList<>();
        String sql = "SELECT * FROM assessments WHERE title LIKE ?";
        String[] args = {"%" + query + "%"};
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            results.add(AssessmentFactory.fromCursor(cursor));
        }
        cursor.close();
        return results;
    }

}

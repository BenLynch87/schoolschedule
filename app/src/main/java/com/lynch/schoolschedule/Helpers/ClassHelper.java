package com.lynch.schoolschedule.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;
import com.lynch.schoolschedule.Entities.ClassEntity;

import java.util.ArrayList;
import java.util.List;

public class ClassHelper {

    private final SchoolScheduleDbHelper dbHelper;

    public ClassHelper(Context context) {
        this.dbHelper = new SchoolScheduleDbHelper(context);
    }

    public long insertClass(ClassEntity cls) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("term_id", cls.getTermId());
        values.put("name", cls.getClassName());
        values.put("instructor", cls.getInstructor());
        values.put("start_date", cls.getStartDate());
        values.put("end_date", cls.getEndDate());
        values.put("status", cls.getStatus());
        values.put("notes", cls.getNotes());
        return db.insert("classes", null, values);
    }

    public void updateClass(ClassEntity cls) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("term_id", cls.getTermId());
        values.put("name", cls.getClassName());
        values.put("instructor", cls.getInstructor());
        values.put("start_date", cls.getStartDate());
        values.put("end_date", cls.getEndDate());
        values.put("status", cls.getStatus());
        values.put("notes", cls.getNotes());
        db.update("classes", values, "id = ?", new String[]{String.valueOf(cls.getId())});
    }

    public void deleteClass(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("classes", "id = ?", new String[]{String.valueOf(id)});
    }

    public ClassEntity getClass(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("classes", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ClassEntity cls = new ClassEntity(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("instructor")),
                    cursor.getString(cursor.getColumnIndexOrThrow("start_date")),
                    cursor.getString(cursor.getColumnIndexOrThrow("end_date")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("term_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("notes"))
            );
            cursor.close();
            return cls;
        }
        return null;
    }

    public List<ClassEntity> getAllClasses() {
        List<ClassEntity> classes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("classes", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ClassEntity cls = new ClassEntity(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("instructor")),
                        cursor.getString(cursor.getColumnIndexOrThrow("start_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("end_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("term_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("notes"))
                );
                classes.add(cls);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return classes;
    }

    public List<ClassEntity> getClassesByTermId(int termId) {
        List<ClassEntity> classes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("classes", null, "term_id = ?", new String[]{String.valueOf(termId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ClassEntity cls = new ClassEntity(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("instructor")),
                        cursor.getString(cursor.getColumnIndexOrThrow("start_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("end_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("term_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("notes"))
                );
                classes.add(cls);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return classes;
    }
}

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

    public ClassHelper(Context context) { dbHelper = new SchoolScheduleDbHelper(context);
    }

    public long insertClass(ClassEntity cls) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("term_id", cls.getTermId());
        values.put("name", cls.getClassName());
        values.put("instructor", cls.getInstructor());
        values.put("phone", cls.getPhone());
        values.put("email", cls.getEmail());
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
        values.put("phone", cls.getPhone());
        values.put("email", cls.getEmail());
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
                    cursor.getString(cursor.getColumnIndexOrThrow("notes")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email"))
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
                        cursor.getString(cursor.getColumnIndexOrThrow("notes")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email"))
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
                        cursor.getString(cursor.getColumnIndexOrThrow("notes")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email"))
                );
                classes.add(cls);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return classes;
    }
    public List<ClassEntity> searchClasses(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ClassEntity> results = new ArrayList<>();
        String sql = "SELECT * FROM classes WHERE name LIKE ?";
        String[] args = {"%" + query + "%"};
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String className = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String instructor = cursor.getString(cursor.getColumnIndexOrThrow("instructor"));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
            String endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
            String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
            int termId = cursor.getInt(cursor.getColumnIndexOrThrow("term_id"));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            ClassEntity classEntity = new ClassEntity(id, className, instructor, startDate, endDate, status, termId, notes, phone, email);
            results.add(classEntity);
        }
        cursor.close();
        return results;
    }


}

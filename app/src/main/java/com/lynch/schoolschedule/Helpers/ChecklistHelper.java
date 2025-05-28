package com.lynch.schoolschedule.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;
import com.lynch.schoolschedule.Entities.ChecklistItemEntity;

import java.util.ArrayList;
import java.util.List;

public class ChecklistHelper {

    private final SchoolScheduleDbHelper dbHelper;

    public ChecklistHelper(Context context) {
        dbHelper = new SchoolScheduleDbHelper(context);
    }

    public long insertChecklistItem(ChecklistItemEntity item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class_id", item.getClassId());
        values.put("content", item.getContent());
        values.put("is_done", item.isDone() ? 1 : 0);
        return db.insert("checklist_items", null, values);
    }

    public ChecklistItemEntity getChecklistItem(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("checklist_items", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ChecklistItemEntity item = new ChecklistItemEntity(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("class_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("content")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("is_done")) == 1
            );
            cursor.close();
            return item;
        }
        return null;
    }

    public List<ChecklistItemEntity> getChecklistItemsForClass(int classId) {
        List<ChecklistItemEntity> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("checklist_items", null, "class_id = ?", new String[]{String.valueOf(classId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ChecklistItemEntity item = new ChecklistItemEntity(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("class_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("content")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("is_done")) == 1
                );
                list.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public int updateChecklistItem(ChecklistItemEntity item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class_id", item.getClassId());
        values.put("content", item.getContent());
        values.put("is_done", item.isDone() ? 1 : 0);
        return db.update("checklist_items", values, "id = ?", new String[]{String.valueOf(item.getId())});
    }

    public int deleteChecklistItem(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("checklist_items", "id = ?", new String[]{String.valueOf(id)});
    }
}

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

    /**
     * Insert a new checklist item.
     */
    public long insertChecklistItem(ChecklistItemEntity item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("assessment_id", item.getAssessmentId());
        values.put("text", item.getContent());
        values.put("checked", item.isDone() ? 1 : 0);
        return db.insert("checklist_items", null, values);
    }

    /**
     * Update an existing checklist item.
     */
    public int updateChecklistItem(ChecklistItemEntity item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("assessment_id", item.getAssessmentId());
        values.put("text", item.getContent());
        values.put("checked", item.isDone() ? 1 : 0);
        return db.update("checklist_items", values, "id=?", new String[]{String.valueOf(item.getId())});
    }

    /**
     * Retrieve all checklist items for a specific assessment.
     */
    public List<ChecklistItemEntity> getChecklistItemsForAssessment(int assessmentId) {
        List<ChecklistItemEntity> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "checklist_items",
                null,
                "assessment_id=?",
                new String[]{String.valueOf(assessmentId)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ChecklistItemEntity item = new ChecklistItemEntity(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("assessment_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("text")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("checked")) == 1
                );
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    /**
     * Delete all checklist items for a specific assessment.
     */
    public void deleteChecklistItemsForAssessment(int assessmentId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("checklist_items", "assessment_id=?", new String[]{String.valueOf(assessmentId)});
    }
}

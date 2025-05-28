package com.lynch.schoolschedule.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Assessments.ObjectiveAssessment;
import com.lynch.schoolschedule.Assessments.PerformanceAssessment;
import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;

import java.util.ArrayList;
import java.util.List;

public class AssessmentHelper {

    private final SchoolScheduleDbHelper dbHelper;

    public AssessmentHelper(Context context) {
        dbHelper = new SchoolScheduleDbHelper(context);
    }

    public long insertAssessment(Assessment assessment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class_id", assessment.getClassId());
        values.put("title", assessment.getTitle());
        values.put("due_date", assessment.getDueDate());

        if (assessment instanceof ObjectiveAssessment) {
            values.put("type", "objective");
        } else if (assessment instanceof PerformanceAssessment) {
            values.put("type", "performance");
        } else {
            values.put("type", "generic");
        }

        return db.insert("assessments", null, values);
    }

    public Assessment getAssessment(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("assessments", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Assessment assessment = fromCursor(cursor);
            cursor.close();
            return assessment;
        }
        return null;
    }

    public List<Assessment> getAllAssessments() {
        List<Assessment> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("assessments", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public int updateAssessment(Assessment assessment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class_id", assessment.getClassId());
        values.put("title", assessment.getTitle());
        values.put("due_date", assessment.getDueDate());

        if (assessment instanceof ObjectiveAssessment) {
            values.put("type", "objective");
        } else if (assessment instanceof PerformanceAssessment) {
            values.put("type", "performance");
        } else {
            values.put("type", "generic");
        }

        return db.update("assessments", values, "id = ?", new String[]{String.valueOf(assessment.getId())});
    }

    public int deleteAssessment(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("assessments", "id = ?", new String[]{String.valueOf(id)});
    }

    private Assessment fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        int classId = cursor.getInt(cursor.getColumnIndexOrThrow("class_id"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("due_date"));
        String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));

        Assessment a;
        switch (type) {
            case "objective":
                a = new ObjectiveAssessment(id, classId, title, dueDate);
                break;
            case "performance":
                a = new PerformanceAssessment(id, classId, title, dueDate);
                break;
            default:
                a = new Assessment(id, classId, title, dueDate);
        }
        return a;
    }
}

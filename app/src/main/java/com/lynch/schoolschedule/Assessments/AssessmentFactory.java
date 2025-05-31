package com.lynch.schoolschedule.Assessments;

import android.database.Cursor;

import com.lynch.schoolschedule.Assessments.Assessment;

import java.time.LocalDateTime;

public class AssessmentFactory {

    public static Assessment createAssessment(int id, int classId, String title, LocalDateTime dueDate, String type) {
        Assessment assessment = new Assessment();
        assessment.setId(id);
        assessment.setClassId(classId);
        assessment.setTitle(title);
        assessment.setDueDate(dueDate.toString());
        assessment.setType(type);
        return assessment;
    }
    public static Assessment fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        int classId = cursor.getInt(cursor.getColumnIndexOrThrow("class_id"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("due_date"));
        String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));

        // Determine which subclass to instantiate
        if ("Performance".equalsIgnoreCase(type)) {
            return new PerformanceAssessment(id, classId, title, dueDate, type);
        } else {
            return new ObjectiveAssessment(id, classId, title, dueDate, type);
        }
    }
}

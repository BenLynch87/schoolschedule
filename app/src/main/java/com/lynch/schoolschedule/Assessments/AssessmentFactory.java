package com.lynch.schoolschedule.Assessments;

public class AssessmentFactory {
    public static Assessment createAssessment(String type, int id, String title, java.time.LocalDateTime dueDate, int classId) {
        return new Assessment(id, classId, title, null, dueDate, type);
    }
}

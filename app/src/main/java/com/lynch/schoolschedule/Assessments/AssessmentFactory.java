package com.lynch.schoolschedule.Assessments;

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
}

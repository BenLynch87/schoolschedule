package com.lynch.schoolschedule.Database;

import android.content.Context;
import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.Helpers.AssessmentHelper;
import com.lynch.schoolschedule.Helpers.TermHelper;
import com.lynch.schoolschedule.Helpers.ClassHelper;

import java.time.LocalDateTime;
import java.util.List;

public class Repository {
    private static Repository instance;
    private final AssessmentHelper assessmentHelper;
    private final TermHelper termHelper;
    private final ClassHelper classHelper;

    private Repository(Context context) {
        assessmentHelper = new AssessmentHelper(context);
        termHelper = new TermHelper(context);
        classHelper = new ClassHelper(context);
    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context.getApplicationContext());
        }
        return instance;
    }

    // Existing methods...

    // 🆕 Assessments due in next week
    public List<Assessment> getAssessmentsDueInNextWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);
        return assessmentHelper.getAssessmentsDueBetween(now, nextWeek);
    }

    // 🆕 Assessments due in next month
    public List<Assessment> getAssessmentsDueInNextMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMonth = now.plusMonths(1);
        return assessmentHelper.getAssessmentsDueBetween(now, nextMonth);
    }

    // 🆕 Get Term by name
    public TermEntity getTermByName(String name) {
        return termHelper.getTermByName(name);
    }


    public List<Assessment> getAssessmentsByTermId(int termId) {
        return assessmentHelper.getAssessmentsByTermId(termId);
    }

    public List<Assessment> getAssessmentsByClassId(int classId) {
        return assessmentHelper.getAssessmentsByClassId(classId);
    }

    public List<ClassEntity> getClassesByTermId(int termId) {
        return classHelper.getClassesByTermId(termId);
    }

    public void insertAssessment(Assessment assessment) {
        assessmentHelper.insertAssessment(assessment);
    }
    public void insertTerm(TermEntity term) {
        termHelper.insertTerm(term);
    }
    public void updateTerm(TermEntity term) {
        termHelper.updateTerm(term);
    }
    public void deleteTerm(int termId) {
        termHelper.deleteTerm(termId);
    }
    public void insertClass(ClassEntity classEntity) {
        classHelper.insertClass(classEntity);
    }

    public void updateClass(ClassEntity classEntity) {
        classHelper.updateClass(classEntity);
    }
    public void deleteClass(int classId) {
        classHelper.deleteClass(classId);
    }
    public void updateAssessment(Assessment assessment) {
        assessmentHelper.updateAssessment(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        assessmentHelper.deleteAssessment(assessment.getId());
    }

    public List<TermEntity> getAllTerms() {
        return termHelper.getAllTerms();
    }
    public List<Assessment> getAllAssessments() {
        return assessmentHelper.getAllAssessments();
    }
    public Assessment getAssessment(int id) {
        return assessmentHelper.getAssessment(id);
    }
    public List<ClassEntity> getAllClasses() {
        return classHelper.getAllClasses();
    }
    public ClassEntity getClassById(int classId) {
        return classHelper.getClass(classId);
    }
    public TermEntity getTermById(int termId) {
        return termHelper.getTerm(termId);
    }
    public List<TermEntity> searchTerms(String query) {
        return termHelper.searchTerms(query);
    }

    public List<ClassEntity> searchClasses(String query) {
        return classHelper.searchClasses(query);
    }

    public List<Assessment> searchAssessments(String query) {
        return assessmentHelper.searchAssessments(query);
    }
}

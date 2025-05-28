package com.lynch.schoolschedule.Database;

import android.app.Application;

import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Entities.ChecklistItemEntity;
import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.Entities.UserEntity;
import com.lynch.schoolschedule.helper.AssessmentHelper;
import com.lynch.schoolschedule.helper.ChecklistHelper;
import com.lynch.schoolschedule.helper.ClassHelper;
import com.lynch.schoolschedule.helper.TermHelper;
import com.lynch.schoolschedule.helper.UserHelper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final ChecklistHelper checklistHelper;
    private final ClassHelper classHelper;
    private final TermHelper termHelper;
    private final UserHelper userHelper;
    private final AssessmentHelper assessmentHelper;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Repository(Application application) {
        DatabaseManager db = DatabaseManager.getInstance(application);
        checklistHelper = db.getChecklistHelper();
        classHelper = db.getClassHelper();
        termHelper = db.getTermHelper();
        userHelper = db.getUserHelper();
        assessmentHelper = db.getAssessmentHelper();
    }

    public void insertChecklistItem(ChecklistItemEntity item) {
        executor.execute(() -> checklistHelper.insertChecklistItem(item));
    }

    public void deleteChecklistItem(long id) {
        executor.execute(() -> checklistHelper.deleteChecklistItem(id));
    }

    public void insertClass(ClassEntity course) {
        executor.execute(() -> classHelper.insertClass(course));
    }

    public void updateClass(ClassEntity course) {
        executor.execute(() -> classHelper.updateClass(course));
    }

    public void deleteClass(long id) {
        executor.execute(() -> classHelper.deleteClass(id));
    }

    public void insertTerm(TermEntity term) {
        executor.execute(() -> termHelper.insertTerm(term));
    }

    public void updateTerm(TermEntity term) {
        executor.execute(() -> termHelper.updateTerm(term));
    }

    public void deleteTerm(long id) {
        executor.execute(() -> termHelper.deleteTerm(id));
    }

    public void insertUser(UserEntity user) {
        executor.execute(() -> userHelper.insertUser(user));
    }

    public void updateUser(UserEntity user) {
        executor.execute(() -> userHelper.updateUser(user));
    }

    public void deleteUser(long id) {
        executor.execute(() -> userHelper.deleteUser(id));
    }

    public void insertAssessment(Assessment assessment) {
        executor.execute(() -> assessmentHelper.insertAssessment(assessment));
    }

    public void updateAssessment(Assessment assessment) {
        executor.execute(() -> assessmentHelper.updateAssessment(assessment));
    }

    public void deleteAssessment(long id) {
        executor.execute(() -> assessmentHelper.deleteAssessment(id));
    }

    public ChecklistItemEntity getChecklistItem(long id) {
        return checklistHelper.getChecklistItem(id);
    }

    public List<ChecklistItemEntity> getChecklistItemsForClass(int classId) {
        return checklistHelper.getChecklistItemsForClass(classId);
    }

    public ClassEntity getClass(long id) {
        return classHelper.getClass(id);
    }

    public List<ClassEntity> getAllClasses() {
        return classHelper.getAllClasses();
    }

    public TermEntity getTerm(long id) {
        return termHelper.getTerm(id);
    }

    public List<TermEntity> getAllTerms() {
        return termHelper.getAllTerms();
    }

    public UserEntity getUser(long id) {
        return userHelper.getUser(id);
    }

    public List<UserEntity> getAllUsers() {
        return userHelper.getAllUsers();
    }

    public Assessment getAssessment(long id) {
        return assessmentHelper.getAssessment(id);
    }

    public List<Assessment> getAllAssessments() {
        return assessmentHelper.getAllAssessments();
    }
}

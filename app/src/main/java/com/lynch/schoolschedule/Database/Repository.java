package com.lynch.schoolschedule.Database;

import android.content.Context;

import com.lynch.schoolschedule.Entities.ClassEntity;
import com.lynch.schoolschedule.Entities.TermEntity;
import com.lynch.schoolschedule.Assessments.Assessment;
import com.lynch.schoolschedule.Helpers.ClassHelper;
import com.lynch.schoolschedule.Helpers.TermHelper;
import com.lynch.schoolschedule.Helpers.AssessmentHelper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private static Repository instance;
    private final ClassHelper classHelper;
    private final TermHelper termHelper;
    private final AssessmentHelper assessmentHelper;
    private final ExecutorService executor;
    private final Context context;

    private Repository(Context context) {
        this.context = context.getApplicationContext();
        classHelper = new ClassHelper(this.context);
        termHelper = new TermHelper(this.context);
        assessmentHelper = new AssessmentHelper(this.context);
        executor = Executors.newFixedThreadPool(4);
    }

    public static synchronized Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public void insertClass(ClassEntity cls) {
        executor.execute(() -> classHelper.insertClass(cls));
    }

    public void updateClass(ClassEntity cls) {
        executor.execute(() -> classHelper.updateClass(cls));
    }

    public void deleteClass(ClassEntity id) {
        executor.execute(() -> classHelper.deleteClass(id.getId()));
    }

    public ClassEntity getClass(long id) {
        return classHelper.getClass((int) id);
    }

    public List<ClassEntity> getAllClasses() {
        return classHelper.getAllClasses();
    }

    public List<ClassEntity> getClassesByTermId(int termId) {
        return classHelper.getClassesByTermId(termId);
    }

    public List<Assessment> getAssessmentsByClassId(int classId) {
        return assessmentHelper.getAssessmentsByClassId(classId);
    }

    public void insertTerm(TermEntity term) {
        executor.execute(() -> termHelper.insertTerm(term));
    }

    public void updateTerm(TermEntity term) {
        executor.execute(() -> termHelper.updateTerm(term));
    }

    public void deleteTerm(long id) {
        executor.execute(() -> termHelper.deleteTerm((int) id));
    }

    public TermEntity getTerm(long id) {
        return termHelper.getTerm((int) id);
    }

    public List<TermEntity> getAllTerms() {
        return termHelper.getAllTerms();
    }

    public void insertAssessment(Assessment assessment) {
        executor.execute(() -> assessmentHelper.insertAssessment(assessment));
    }

    public void updateAssessment(Assessment assessment) {
        executor.execute(() -> assessmentHelper.updateAssessment(assessment));
    }

    public void deleteAssessment(int id) {
        executor.execute(() -> assessmentHelper.deleteAssessment(id));
    }

    public Assessment getAssessment(int id) {
        return assessmentHelper.getAssessment(id);
    }

    public List<Assessment> getAllAssessments() {
        return assessmentHelper.getAllAssessments();
    }
}

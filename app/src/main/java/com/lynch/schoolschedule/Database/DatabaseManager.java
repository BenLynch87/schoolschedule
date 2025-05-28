package com.lynch.schoolschedule.Database;

import android.content.Context;

import com.lynch.schoolschedule.helper.AssessmentHelper;
import com.lynch.schoolschedule.helper.ChecklistHelper;
import com.lynch.schoolschedule.helper.ClassHelper;
import com.lynch.schoolschedule.helper.TermHelper;
import com.lynch.schoolschedule.helper.UserHelper;

public class DatabaseManager {

    private static DatabaseManager instance;

    private final UserHelper userHelper;
    private final TermHelper termHelper;
    private final ClassHelper classHelper;
    private final ChecklistHelper checklistHelper;
    private final AssessmentHelper assessmentHelper;

    private DatabaseManager(Context context) {
        userHelper = new UserHelper(context);
        termHelper = new TermHelper(context);
        classHelper = new ClassHelper(context);
        checklistHelper = new ChecklistHelper(context);
        assessmentHelper = new AssessmentHelper(context);
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }

    public UserHelper getUserHelper() {
        return userHelper;
    }

    public TermHelper getTermHelper() {
        return termHelper;
    }

    public ClassHelper getClassHelper() {
        return classHelper;
    }

    public ChecklistHelper getChecklistHelper() {
        return checklistHelper;
    }

    public AssessmentHelper getAssessmentHelper() {
        return assessmentHelper;
    }
}

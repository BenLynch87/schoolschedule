package com.lynch.schoolschedule;

import android.app.Application;
import com.lynch.schoolschedule.Helpers.SessionManager;

public class SchoolScheduleApp extends Application {

    private SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = new SessionManager(this);
        if (!sessionManager.isRememberMeEnabled()) {
            sessionManager.logout();
        }
    }

    // Setter for tests
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
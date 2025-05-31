package com.lynch.schoolschedule;

import android.app.Application;
import com.lynch.schoolschedule.Helpers.SessionManager;

public class SchoolScheduleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SessionManager sessionManager = new SessionManager(this);

        // Only clear session on cold start if Remember Me is not enabled
        if (!sessionManager.isRememberMeEnabled()) {
            sessionManager.logout();
        }
    }
}

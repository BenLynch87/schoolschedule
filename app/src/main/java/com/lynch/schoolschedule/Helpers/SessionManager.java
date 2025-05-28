package com.lynch.schoolschedule.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LOGGED_IN = "is_logged_in";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(String username) {
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getLoggedInUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}

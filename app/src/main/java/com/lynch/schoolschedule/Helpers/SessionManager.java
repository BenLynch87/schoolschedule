package com.lynch.schoolschedule.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LOGGED_IN = "is_logged_in";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(String username, boolean rememberMe) {
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
        editor.apply();
    }


    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public boolean isRememberMeEnabled() {
        return prefs.getBoolean(KEY_REMEMBER_ME, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}

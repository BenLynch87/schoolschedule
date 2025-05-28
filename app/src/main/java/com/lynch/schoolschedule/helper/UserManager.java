package com.lynch.schoolschedule.helper;

import android.content.Context;
import android.text.TextUtils;

import com.lynch.schoolschedule.Database.DatabaseManager;
import com.lynch.schoolschedule.Entities.UserEntity;
import com.lynch.schoolschedule.Users.Student;
import com.lynch.schoolschedule.Users.User;

public class UserManager {

    private final Context context;

    public UserManager(Context context) {
        this.context = context;
    }

    public void registerUser(String username, String password, UserCallback callback) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            callback.onFailure("Username and password must not be empty.");
            return;
        }

        new Thread(() -> {
            UserEntity existingUser = DatabaseManager.getInstance(context).getUserHelper().getUserByUsername(username);
            if (existingUser != null) {
                callback.onFailure("Username already exists.");
            } else {
                UserEntity newUser = new UserEntity(username, password);
                DatabaseManager.getInstance(context).getUserHelper().insertUser(newUser);
                callback.onSuccess();
            }
        }).start();
    }

    public void authenticateUser(String username, String password, AuthCallback callback) {
        new Thread(() -> {
            UserEntity entity = DatabaseManager.getInstance(context).getUserHelper().getUserByUsername(username);
            if (entity != null && entity.getPassword().equals(password)) {
                callback.onAuthenticated(new Student(entity.getName()));
            } else {
                callback.onFailure("Invalid credentials.");
            }
        }).start();
    }

    public interface UserCallback {
        void onSuccess();
        void onFailure(String message);
    }

    public interface AuthCallback {
        void onAuthenticated(User user);
        void onFailure(String message);
    }
}

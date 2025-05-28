
package com.lynch.schoolschedule.Users;

import com.lynch.schoolschedule.Users.User;

public class Student extends User {
    public Student(String username) {
        super(username, ""); // Default password
    }

    public String getWelcomeMessage() {
        return "Welcome, student " + getUsername() + "! Here's your schedule and assignments.";
    }
}

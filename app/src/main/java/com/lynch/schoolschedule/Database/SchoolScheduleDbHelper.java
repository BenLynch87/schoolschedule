package com.lynch.schoolschedule.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SchoolScheduleDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "school_schedule.db";
    private static final int DATABASE_VERSION = 2;

    public SchoolScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT);");
        db.execSQL("CREATE TABLE terms (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
        db.execSQL("CREATE TABLE classes (id INTEGER PRIMARY KEY AUTOINCREMENT, term_id INTEGER, name TEXT, instructor TEXT, start_date TEXT, end_date TEXT, status TEXT, notes TEXT);");
        db.execSQL("CREATE TABLE assessments (id INTEGER PRIMARY KEY AUTOINCREMENT, class_id INTEGER, title TEXT, due_date TEXT, type TEXT);");
        db.execSQL("CREATE TABLE checklist_items (id INTEGER PRIMARY KEY AUTOINCREMENT, assessment_id INTEGER, text TEXT, checked INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE classes ADD COLUMN start_date TEXT;");
            db.execSQL("ALTER TABLE classes ADD COLUMN end_date TEXT;");
            db.execSQL("ALTER TABLE classes ADD COLUMN status TEXT;");
            db.execSQL("ALTER TABLE classes ADD COLUMN notes TEXT;");
        }
    }
}

package com.lynch.schoolschedule.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SchoolScheduleDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "school_schedule.db";
    private static final int DATABASE_VERSION = 4;

    public SchoolScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS terms (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, start_date TEXT NOT NULL, end_date TEXT NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS classes (id INTEGER PRIMARY KEY AUTOINCREMENT, term_id INTEGER, name TEXT, instructor TEXT, phone TEXT, email TEXT, start_date TEXT, end_date TEXT, status TEXT, notes TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS assessments (id INTEGER PRIMARY KEY AUTOINCREMENT, class_id INTEGER, title TEXT, start_date TEXT, due_date TEXT, type TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS checklist_items (id INTEGER PRIMARY KEY AUTOINCREMENT, assessment_id INTEGER, text TEXT, checked INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS terms");
        db.execSQL("DROP TABLE IF EXISTS classes");
        db.execSQL("DROP TABLE IF EXISTS assessments");
        db.execSQL("DROP TABLE IF EXISTS checklist_items");
        onCreate(db);
    }
}

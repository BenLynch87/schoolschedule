package com.lynch.schoolschedule;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lynch.schoolschedule.Database.SchoolScheduleDbHelper;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SchoolScheduleDbHelperTest {

    private Context context;
    private SchoolScheduleDbHelper dbHelper;
    private SQLiteDatabase database;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        dbHelper = new SchoolScheduleDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        database.close();
        dbHelper.close();
    }

    @Test
    public void testOnCreate_ShouldCreateAllTables() {
        assertTrue(checkTableExists("users"));
        assertTrue(checkTableExists("terms"));
        assertTrue(checkTableExists("classes"));
        assertTrue(checkTableExists("assessments"));
        assertTrue(checkTableExists("checklist_items"));
    }

    @Test
    public void testOnUpgrade_ShouldRecreateTables() {
        int oldVersion = 1;
        int newVersion = 4;

        // simulate upgrade
        dbHelper.onUpgrade(database, oldVersion, newVersion);

        assertTrue(checkTableExists("users"));
        assertTrue(checkTableExists("terms"));
        assertTrue(checkTableExists("classes"));
        assertTrue(checkTableExists("assessments"));
        assertTrue(checkTableExists("checklist_items"));
    }

    private boolean checkTableExists(String tableName) {
        Cursor cursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                new String[]{tableName}
        );
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}

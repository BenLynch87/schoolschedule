package com.lynch.schoolschedule.Helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class to assist with date formatting and date picker dialogs.
 */
public class DateHelper {

    private static String selectedStartDate = "";
    private static String selectedEndDate = "";

    private DatePickerDialog datePickerDialog;

    /**
     * Returns today's date as a formatted string.
     * Format: MM/dd/yyyy
     */
    public static String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month + "/" + day + "/" + year;
    }

    /**
     * Formats a date given as day, month, and year.
     */
    public static String makeDateString(int day, int month, int year) {
        return month + "/" + day + "/" + year;
    }

    /**
     * Converts a formatted date string into millis since epoch.
     */
    public static long getMillisFromDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date date = sdf.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    /**
     * Launches a DatePickerDialog and stores the result in selectedStartDate.
     */
    public static void showStartDatePicker(Context context, TextView targetField) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(context, (view, selectedYear, selectedMonth, selectedDay) -> {
            selectedStartDate = makeDateString(selectedDay, selectedMonth + 1, selectedYear);
            targetField.setText(selectedStartDate);
        }, year, month, day);

        picker.show();
    }

    /**
     * Launches a DatePickerDialog and stores the result in selectedEndDate.
     */
    public static void showEndDatePicker(Context context, TextView targetField) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(context, (view, selectedYear, selectedMonth, selectedDay) -> {
            selectedEndDate = makeDateString(selectedDay, selectedMonth + 1, selectedYear);
            targetField.setText(selectedEndDate);
        }, year, month, day);

        picker.show();
    }

    public static String getSelectedStartDate() {
        return selectedStartDate.isEmpty() ? getTodaysDate() : selectedStartDate;
    }

    public static String getSelectedEndDate() {
        return selectedEndDate.isEmpty() ? getTodaysDate() : selectedEndDate;
    }
}

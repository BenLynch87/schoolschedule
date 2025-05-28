package com.lynch.schoolschedule.Assessments;

public class Assessment {
    private int id;
    private int classId;
    private String title;
    private String dueDate;

    public Assessment(int id, int classId, String title, String dueDate) {
        this.id = id;
        this.classId = classId;
        this.title = title;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public int getClassId() {
        return classId;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
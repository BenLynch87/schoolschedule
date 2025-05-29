package com.lynch.schoolschedule.Assessments;

public class Assessment {

    private int id;
    private int classId;
    private String title;
    private String dueDate;
    private String type;

    public Assessment(int id, int classId, String title, String dueDate) {
        this.id = id;
        this.classId = classId;
        this.title = title;
        this.dueDate = dueDate;
    }
    public Assessment() {
        this(0, 0, "", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReportLine() {
        return "Assessment: " + title + " | Type: " + type + " | Due: " + dueDate;
    }
}

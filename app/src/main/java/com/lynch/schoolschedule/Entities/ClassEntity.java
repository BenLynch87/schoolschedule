package com.lynch.schoolschedule.Entities;

public class ClassEntity {
    private int id;
    private int termId;
    private String name;
    private String instructor;
    private String startDate;
    private String endDate;
    private String status;
    private String notes;

    public ClassEntity(int id, int termId, String name, String instructor, String startDate, String endDate, String status, String notes) {
        this.id = id;
        this.termId = termId;
        this.name = name;
        this.instructor = instructor;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public int getTermId() {
        return termId;
    }

    public String getName() {
        return name;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

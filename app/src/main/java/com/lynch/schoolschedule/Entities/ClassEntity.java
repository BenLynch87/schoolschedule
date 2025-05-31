package com.lynch.schoolschedule.Entities;

public class ClassEntity {
    private int id;
    private String className;
    private String instructor;
    private String startDate;
    private String endDate;
    private String status;
    private int termId;
    private String notes;
    private String phone;
    private String email;

    public ClassEntity(int id, String className, String instructor, String startDate, String endDate, String status, int termId, String notes, String phone, String email) {
        this.id = id;
        this.className = className;
        this.instructor = instructor;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termId = termId;
        this.notes = notes;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
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

    public int getTermId() {
        return termId;
    }

    public String getNotes() {
        return notes;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setPhone(String phone) {
        this.notes = phone;
    }
    public void setEmail(String email) {
        this.notes = email;
    }

}

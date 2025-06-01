package com.lynch.schoolschedule.Entities;

public class TermEntity {
    private int id;
    private String termName;
    private String startDate;
    private String endDate;

    public TermEntity(int id, String termName, String startDate, String endDate) {
        this.id = id;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TermEntity() {
        this.id = 0;
        this.termName = "default";
        this.startDate = "default";
        this.endDate = "default";
    }

    public int getId() {
        return id;
    }

    public String getTermName() {
        return termName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

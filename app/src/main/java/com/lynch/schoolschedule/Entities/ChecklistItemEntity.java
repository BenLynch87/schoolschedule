package com.lynch.schoolschedule.Entities;

public class ChecklistItemEntity {
    private int id;
    private int assessmentId;
    private String content;
    private boolean isDone;

    public ChecklistItemEntity(int id, int assessmentId, String content, boolean isDone) {
        this.id = id;
        this.assessmentId = assessmentId;
        this.content = content;
        this.isDone = isDone;
    }

    public ChecklistItemEntity(int classId, String content, boolean isDone) {
        this.assessmentId = classId;
        this.content = content;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
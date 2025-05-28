package com.lynch.schoolschedule.Entities;

public class ChecklistItemEntity {
    private int id;
    private int classId;
    private String content;
    private boolean isDone;

    public ChecklistItemEntity(int id, int classId, String content, boolean isDone) {
        this.id = id;
        this.classId = classId;
        this.content = content;
        this.isDone = isDone;
    }

    public ChecklistItemEntity(int classId, String content, boolean isDone) {
        this.classId = classId;
        this.content = content;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public int getClassId() {
        return classId;
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

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
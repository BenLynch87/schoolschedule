package com.lynch.schoolschedule.Entities;

public class UserEntity {
    private int id;
    private String name;

    private String password;

    public UserEntity(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserEntity() {
        this.id=1;
        this.name = "default";
        this.password = "default";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}

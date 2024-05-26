package com.example.demo.entities;

public class User {
    private int id;
    private String fullName;
    private String cnp;

    public User() {}

    public User(String fullName, String cnp) {
        this.fullName = fullName;
        this.cnp = cnp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}

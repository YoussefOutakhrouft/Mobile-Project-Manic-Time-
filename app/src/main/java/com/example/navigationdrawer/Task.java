package com.example.navigationdrawer;

public class Task {
    private String taskName;
    private String taskDescription;
    private String date;

    // Constructeur par défaut requis par Firebase
    public Task() {}

    public Task(String taskName, String taskDescription, String date) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.date = date;
    }

    // Getters et Setters
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

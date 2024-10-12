package com.example.projectmanagerapplication;

public class Task {
    int id,projectId;
    String name,assignee,dueDate,status;
    public Task(int id, String name, String assignee, String dueDate, String status , int projectId) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.status = status;
        this.projectId = projectId ;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateStatus(String s) {
        this.status = s;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}

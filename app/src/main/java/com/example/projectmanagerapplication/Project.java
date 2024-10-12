package com.example.projectmanagerapplication;

public class Project {
    private int id , userID;

    private int nbTasks,nbActiveTasks;
    private double progress;
    private String projectName;
    private String dueDate;
    private Task[] tasks;

    public Project() {
    }


    public Project(int id, String projectName, String dueDate ) {
        this.id = id;
        this.projectName = projectName;
        this.dueDate = dueDate;
        this.progress=0.0;
        this.nbActiveTasks=0;
    }


    public Project(int id, String projectName, String dueDate , int userID) {
        this.id = id;
        this.projectName = projectName;
        this.dueDate = dueDate;
        this.progress=0.0;
        this.nbActiveTasks=0;
        this.userID=userID;
    }



    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    //  Tasks progress
    public double calculateProgress() {
        int completedTask = 0;
        if (nbTasks == 0) {
            return 0.0;
        }
        for (Task task : tasks) {
            if (task.getStatus() == "COMPLETED")
                completedTask ++;
        }
        return (double) completedTask / nbTasks;
    }

    public int calculateActiveTasks() {
        int activeTasks = 0;
        for (Task task : tasks) {
            if (task.getStatus() != "COMPLETED")
                activeTasks++;
        }
        return activeTasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbTasks() {
        return nbTasks;
    }

    public void setNbTasks(int nbTasks) {
        this.nbTasks = nbTasks;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getNbActiveTasks() {
        return nbActiveTasks;
    }

    public void setNbActiveTasks(int nbActiveTasks) { this.nbActiveTasks = nbActiveTasks;}



    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

}

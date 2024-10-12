package com.example.projectmanagerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public Helper(@Nullable Context context) {
        super(context, "projectManager", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE projects(_id INTEGER PRIMARY KEY AUTOINCREMENT, projectName TEXT, dueDate TEXT, progress REAL DEFAULT 0, activeTasks INTEGER DEFAULT 0, userID INTEGER, FOREIGN KEY(userID) REFERENCES users(_id))");
        db.execSQL("CREATE TABLE tasks(_id INTEGER PRIMARY KEY AUTOINCREMENT, taskName TEXT , assignee TEXT, dueDate TEXT, status TEXT , projectID INTEGER, FOREIGN KEY(projectID) REFERENCES projects(_id) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS projects");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", user.getName());
        cv.put("password", user.getPassword());

        db.insert("users", null, cv);
        db.close();
    }

    public Boolean isUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM users WHERE username = ? AND password = ?", new String[]{username, password});

        boolean exists = false;
        if (c != null && c.moveToFirst()) {
            exists = c.getInt(0) > 0;
        }
        if (c != null) {
            c.close();
        }
        return exists;
    }


    public  int getUserID (String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM users WHERE username = ?", new String[]{username});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }

    public void addProject(Project p)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("projectName",p.getProjectName());
        cv.put("dueDate",p.getDueDate());
        cv.put("progress", 0);
        cv.put("activeTasks", 0);
        cv.put("userID", p.getUserID());

        db.insert("projects",null,cv);
        db.close();
    }

    public void updateProject(Project p){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();

        cv.put("projectName", p.getProjectName());
        cv.put("dueDate", p.getDueDate());

        double progress = calculateProjectProgress(p.getId());
        int activeTasks = getNumberOfActiveTasksForProject(p.getId());

        cv.put("progress", progress);
        cv.put("activeTasks", activeTasks);

        db.update("projects",cv,"_id=?",new String[]{String.valueOf(p.getId())} );
        db.close();
    }


    private void updateProjectProgressAndActiveTasks(int projectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        double progress = calculateProjectProgress(projectId);
        int activeTasks = getNumberOfActiveTasksForProject(projectId);

        cv.put("progress", progress);
        cv.put("activeTasks", activeTasks);

        db.update("projects", cv, "_id=?", new String[]{String.valueOf(projectId)});
        db.close();
    }

    public void deleteProject(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("projects","_id=?",new String[]{String.valueOf(id)});
        db.delete("tasks","projectID=?",new String[]{String.valueOf(id)});
        db.close();
    }

    private double calculateProjectProgress(int projectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM tasks WHERE projectID = ?", new String[]{String.valueOf(projectId)});
        int totalTasks = 0;
        if (c != null && c.moveToFirst()) {
            totalTasks = c.getInt(0);
        }
        c.close();

        c = db.rawQuery("SELECT COUNT(*) FROM tasks WHERE projectID = ? AND status = 'COMPLETED'", new String[]{String.valueOf(projectId)});
        int completedTasks = 0;
        if (c != null && c.moveToFirst()) {
            completedTasks = c.getInt(0);
        }
        c.close();

        if (totalTasks == 0) {return 0.0;}
        return (double) completedTasks / totalTasks * 100;
    }

    private int getNumberOfActiveTasksForProject(int projectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM tasks WHERE projectID = ? AND status != 'COMPLETED'", new String[]{String.valueOf(projectId)});
        int activeTasks = 0;
        if (c != null && c.moveToFirst()) {
            activeTasks = c.getInt(0);
        }
        c.close();
        return activeTasks;
    }

    public void addTask(Task task)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("taskName", task.getName());
        cv.put("assignee", task.getAssignee());
        cv.put("dueDate", task.getDueDate());
        cv.put("status", task.getStatus());
        cv.put("projectID", task.getProjectId());

        db.insert("tasks",null,cv);
        db.close();

        updateProjectProgressAndActiveTasks(task.getProjectId());
    }

    public void updateTaskStatus(int taskId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("status", status);
        db.update("tasks", cv, "_id=?", new String[]{String.valueOf(taskId)});
        db.close();

        updateProjectProgressAndActiveTasks(getProjectIdForTask(taskId));
    }

    public void deleteTask(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("tasks","_id=?",new String[]{String.valueOf(id)});
        db.close();

        updateProjectProgressAndActiveTasks(getProjectIdForTask(id));
    }

    public Cursor getAllProjects(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM projects WHERE userID = ?", new String[]{String.valueOf(userId)});
    }

    /**
    public Cursor getAllProjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM projects", null);
    }
     **/

    public Project getOneProject(int id )
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.query("projects",new String[]{"_id","projectName", "dueDate"},"_id=?", new String[]{String.valueOf(id)},null,null,null);

        c.moveToFirst();
        Project p = new Project(c.getInt(0),c.getString(1),c.getString(2));
        p.setProgress(c.getDouble(3));
        p.setNbActiveTasks(c.getInt(4));
        c.close();

        return p;
    }

    public Cursor getAllProjectTasks(int projectId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tasks WHERE projectID = ?", new String[]{String.valueOf(projectId)});
        return c;
    }


    private int getProjectIdForTask(int taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT projectID FROM tasks WHERE _id = ?", new String[]{String.valueOf(taskId)});
        int projectId = -1;
        if (c != null && c.moveToFirst()) {
            projectId = c.getInt(0);
        }
        c.close();
        return projectId;
    }


}

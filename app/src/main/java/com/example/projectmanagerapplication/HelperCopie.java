package com.example.projectmanagerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperCopie extends SQLiteOpenHelper {
    public HelperCopie(@Nullable Context context) {
        super(context, "projectManager", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE projects(_id INTEGER PRIMARY KEY AUTOINCREMENT, projectName TEXT , dueDate TEXT )");
        db.execSQL("CREATE TABLE tasks(_id INTEGER PRIMARY KEY AUTOINCREMENT, taskName TEXT , assignee TEXT, dueDate TEXT, status TEXT , projectID INTEGER, FOREIGN KEY(projectID) REFERENCES projects(_id) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS projects");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    public void addProject(Project p)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("projectName",p.getProjectName());
        cv.put("dueDate",p.getDueDate());

        db.insert("projects",null,cv);
        db.close();
    }

    public void updateProject(Project p){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("projectName",p.getProjectName());
        cv.put("dueDate",p.getDueDate());

        db.update("projects",cv,"_id=?",new String[]{String.valueOf(p.getId())} );
        db.close();
    }

    public void deleteProject(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("projects","_id=?",new String[]{String.valueOf(id)});
        db.delete("tasks","projectID=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void addTask(Task task)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("_id", task.getId());
        cv.put("taskName", task.getName());
        cv.put("assignee", task.getAssignee());
        cv.put("dueDate", task.getDueDate());
        cv.put("status", task.getStatus());
        cv.put("projectID", task.getProjectId());

        db.insert("tasks",null,cv);
        db.close();
    }

    public void updateTaskStatus(int taskId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("status", status);
        db.update("tasks", cv, "_id=?", new String[]{String.valueOf(taskId)});
        db.close();
    }

    public void deleteTask(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("tasks","projectID=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllProjects() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM projects", null);
    }

    public Project getOneProject(int id )
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.query("projects",new String[]{"_id","projectName", "dueDate"},"_id=?", new String[]{String.valueOf(id)},null,null,null);

        c.moveToFirst();
        Project p = new Project(c.getInt(0),c.getString(1),c.getString(2));

        return p;
    }

    public Cursor getAllProjectTasks(int projectId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tasks WHERE projectID = ?", new String[]{String.valueOf(projectId)});
        return c;
    }

    public int getNumberOfTasksForProject(int projectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM tasks WHERE projectID = ?", new String[]{String.valueOf(projectId)});
        c.moveToFirst();
        int count = c.getInt(0);
        c.close();
        return count;
    }

    public double calculateProjectProgress(int projectId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor totalTasksCursor = db.rawQuery("SELECT COUNT(*) FROM tasks WHERE projectID = ?", new String[]{String.valueOf(projectId)});
        totalTasksCursor.moveToFirst();
        int totalTasks = totalTasksCursor.getInt(0);
        totalTasksCursor.close();

        Cursor completedTasksCursor = db.rawQuery("SELECT COUNT(*) FROM tasks WHERE projectID = ? AND status = 'COMPLETED'", new String[]{String.valueOf(projectId)});
        completedTasksCursor.moveToFirst();
        int completedTasks = completedTasksCursor.getInt(0);
        completedTasksCursor.close();

        db.close();

        if (totalTasks > 0) {return (double) completedTasks / totalTasks * 100.0;}
        else { return 0.0;}

    }

    public int getNumberOfActiveTasksForProject(int projectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM tasks WHERE projectID = ? AND status != 'COMPLETED'", new String[]{String.valueOf(projectId)});
        c.moveToFirst();
        int count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }
}

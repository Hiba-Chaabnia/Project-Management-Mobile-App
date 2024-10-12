package com.example.projectmanagerapplication

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_project)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var projectId = intent.getIntExtra("projectId", -1)
        var projectName = intent.getStringExtra("projectName")
        var helper = Helper(this)


        var addTaskButton = findViewById<LinearLayout>(R.id.addTask)
        var tasksList = findViewById<ListView>(R.id.tasksList)
        var returnToMain = findViewById<ImageView>(R.id.returnToM)
        var projName = findViewById<TextView>(R.id.projName)
        var deleteProject = findViewById<ImageView>(R.id.deleteProject)

        projName.text = projectName

        returnToMain.setOnClickListener { finish() }

        deleteProject.setOnClickListener {
            var alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Delete Project")
            alertDialogBuilder.setMessage("Do you want to delete this project?")
            alertDialogBuilder.setPositiveButton("Delete") { a, b ->;
                if (projectId != -1)
                {
                    var helper = Helper(this)
                    helper.deleteProject(projectId)
                    Toast.makeText(this, "Project deleted", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "Error deleting project", Toast.LENGTH_SHORT).show()
                }


            }
            alertDialogBuilder.setNegativeButton("Cancel") { c, d ->;finish() }

            var alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        addTaskButton.setOnClickListener {

            var i = Intent (this , AddTaskActivity::class.java)
            i.putExtra("projectId", projectId)
            startActivity(i)

        }


        var cursor: Cursor = helper.getAllProjectTasks(projectId)
        /**
        var columns = arrayOf(cursor.getColumnName(1), cursor.getColumnName(2),cursor.getColumnName(3),cursor.getColumnName(4))
        var views = intArrayOf(R.id.taskNameItem,R.id.assignedTo, R.id.taskDD,R.id.statusText)

        var adapter = SimpleCursorAdapter(this, R.layout.task_item, cursor, columns, views, 0)
        var project = helper.getOneProject(taskProjectID)

        helper.updateProject(project)
        tasksListView.adapter = adapter
        **/

        if (cursor != null && cursor.count > 0) {
            var columns = arrayOf(cursor.getColumnName(1), cursor.getColumnName(2),cursor.getColumnName(3),cursor.getColumnName(4))
            var views = intArrayOf(R.id.taskNameItem,R.id.assignedTo, R.id.taskDD,R.id.statusText)

            var adapter = SimpleCursorAdapter(this, R.layout.task_item, cursor, columns, views, 0)
            tasksList.adapter=adapter;

            tasksList.setOnItemClickListener { a, b, c, d ->
                cursor.moveToPosition(c)
                var taskId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                var taskProjectID = cursor.getInt(cursor.getColumnIndexOrThrow("projectID"))

                var taskDialog = AlertDialog.Builder(this)
                taskDialog.setTitle("Task Options")
                taskDialog.setMessage("Do you want to update the status or delete the task?")
                taskDialog.setPositiveButton("Update Status") { a, b ->

                    var statusDialog = AlertDialog.Builder(this)
                    statusDialog.setTitle("Update Task Status")

                    var statuses = arrayOf("PENDING", "IN PROGRESS", "COMPLETED")
                    var selectedStatus = statuses[0]

                    statusDialog.setSingleChoiceItems(statuses, 0) { a, b -> selectedStatus = statuses[b] }

                    statusDialog.setPositiveButton("Update") { a, b ->
                        helper.updateTaskStatus(taskId, selectedStatus)

                        Toast.makeText(this, "Task Status updated", Toast.LENGTH_SHORT).show()
                        /**var project = helper.getOneProject(taskProjectID)
                        helper.updateProject(project)**/

                    }

                    statusDialog.setNegativeButton("Cancel") { a, b -> a.dismiss() }

                    statusDialog.show()
                }


                taskDialog.setNegativeButton("Delete Task") { a, b ->
                    helper.deleteTask(taskId)
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()

                    /**var project = helper.getOneProject(taskProjectID)
                    helper.updateProject(project)**/
                }

                taskDialog.setNeutralButton("Cancel") { a, b -> a.dismiss() }

                taskDialog.show()
            }
        }
        else
        {
            Toast.makeText(this, "No task found", Toast.LENGTH_LONG).show()
        }
    }
}
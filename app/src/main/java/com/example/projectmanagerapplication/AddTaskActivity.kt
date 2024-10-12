package com.example.projectmanagerapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var helper = Helper(this)

        var taskProjectID = intent.getIntExtra("projectId", -1)

        if (taskProjectID == -1) {
            Toast.makeText(this, "Error: Project ID not received", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        var returnToMain = findViewById<ImageView>(R.id.returnToMain22)
        var taskName = findViewById<EditText>(R.id.addTaskName)
        var assignedTo = findViewById<EditText>(R.id.addAssigned)
        var taskDueDate = findViewById<EditText>(R.id.addTaskDueDate)
        var taskStatus = findViewById<EditText>(R.id.taskStatus)
        var addTaskButton = findViewById<Button>(R.id.taskAdded)

        returnToMain.setOnClickListener { finish() }

        addTaskButton.setOnClickListener {




            var taskName = taskName.text.toString()
            var assignedTo = assignedTo.text.toString()
            var taskDueDate = taskDueDate.text.toString()
            var taskStatus = taskStatus.text.toString()


            /**
            var status = taskStatus.checkedRadioButtonId
            var taskStatus = when (status) {
                R.id.ToDo -> "PENDING"
                R.id.Ongoing -> "IN_PROGRESS"
                R.id.Completed -> "COMPLETED"
                else -> "PENDING"
            }
            **/

            var task = Task(0, taskName, assignedTo, taskDueDate, taskStatus, taskProjectID)
            helper.addTask(task)

            var project = helper.getOneProject(taskProjectID)

            helper.updateProject(project)


            Toast.makeText(this, "Task added successfully", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
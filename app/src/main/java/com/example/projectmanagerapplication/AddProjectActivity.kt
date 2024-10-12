package com.example.projectmanagerapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_project)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var userId = intent.getIntExtra("userID", -1)

        var helper = Helper(this)


        var returnButton = findViewById<ImageView>(R.id.returnToMain)
        var projectName = findViewById<EditText>(R.id.addProjectName)
        var dueDate = findViewById<EditText>(R.id.addProjectDueDate)
        var insertProjectButton = findViewById<Button>(R.id.projectAdded)

        returnButton.setOnClickListener { finish() }

        insertProjectButton.setOnClickListener {

            var p = Project(0, projectName.text.toString(), dueDate.text.toString(),userId)

            helper.addProject(p)

            helper.updateProject(p)

            Toast.makeText(this, "Project added successfully", Toast.LENGTH_LONG).show()
            finish()

        }
    }
}
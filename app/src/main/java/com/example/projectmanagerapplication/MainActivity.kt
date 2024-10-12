package com.example.projectmanagerapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var user: String

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<LinearLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var userId = intent.getIntExtra("userID", -1)
        var userName = intent.getStringExtra("userName")



        var greetingText = findViewById<TextView>(R.id.greetingText)
        var calendarView = findViewById<CalendarView>(R.id.calendar)
        var addProject = findViewById<LinearLayout>(R.id.addProject)
        var projectList = findViewById<ListView>(R.id.projectList)

        greetingText.text = "Welcome $userName"

        addProject.setOnClickListener {
            var intentAddProject = Intent(this, AddProjectActivity::class.java)
            intentAddProject.putExtra("userID",userId)
            startActivity(intentAddProject)
        }

        var helper = Helper(this)
        var cursor: Cursor = helper.getAllProjects(userId)

        if (cursor != null && cursor.count > 0) {
            var columns = arrayOf(cursor.getColumnName(1), cursor.getColumnName(2),cursor.getColumnName(3),cursor.getColumnName(4))
            var views = intArrayOf(R.id.projectNameItem, R.id.projectDD,R.id.progress,R.id.nbActiveTasks)

            var adapter = SimpleCursorAdapter(this, R.layout.project_item, cursor, columns, views, 0)
            projectList.adapter=adapter;

            projectList.setOnItemClickListener { a, b, c, d ->
                cursor.moveToPosition(c)
                var projectId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                var projectName = cursor.getString(cursor.getColumnIndexOrThrow("projectName"))
                var intent = Intent(this, ProjectActivity::class.java)
                intent.putExtra("projectId", projectId)
                intent.putExtra("projectName", projectName)
                startActivity(intent)
                }
        }
        else
        {
            Toast.makeText(this, "No project found", Toast.LENGTH_LONG).show()
        }

    }

}

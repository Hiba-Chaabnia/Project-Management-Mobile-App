package com.example.projectmanagerapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    lateinit var helper: Helper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = Helper(this)

        var username = findViewById<EditText>(R.id.loginUsername)
        var passwordEditText = findViewById<EditText>(R.id.loginPassword)
        var loginButton = findViewById<Button>(R.id.loginButton)
        var signUpButton = findViewById<Button>(R.id.goToSignUp)

        signUpButton.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            var username = username.text.toString()
            var password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (helper.isUser(username, password)) {
                    var userId = helper.getUserID(username)

                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userID", userId)
                    intent.putExtra("userName",username)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()

                    var intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)

                }
            }
            else
            {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
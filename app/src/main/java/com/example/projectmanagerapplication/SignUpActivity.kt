package com.example.projectmanagerapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var username = findViewById<EditText>(R.id.signupUsername)
        var password = findViewById<EditText>(R.id.signupPassword)
        var confirmPasswordEditText = findViewById<EditText>(R.id.signupPasswordConfirm)
        var signUpButton = findViewById<Button>(R.id.signupButton)

        signUpButton.setOnClickListener {
            var username = username.text.toString()
            var password = password.text.toString()
            var confirmPassword = confirmPasswordEditText.text.toString()

            var helper = Helper(this)

            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword)
                {
                    var user = User(0, username, password)

                    helper.addUser(user)
                }
                else
                {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}
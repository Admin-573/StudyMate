package com.example.studymate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

@SuppressLint("MissingInflatedId")
class MainActivity : AppCompatActivity() {

    private lateinit var btnAdmin: Button
    private lateinit var btnFaculty: Button
    private lateinit var btnStudent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdmin = findViewById(R.id.btnAdmin)
        btnFaculty = findViewById(R.id.btnFaculty)
        btnStudent = findViewById(R.id.btnStudent)

        btnAdmin.setOnClickListener {
            val Admin = Intent(applicationContext, Admin::class.java)
            startActivity(Admin)
        }
        btnFaculty.setOnClickListener {
            val Faculty = Intent(applicationContext,Faculty::class.java)
            startActivity(Faculty)
        }
        btnStudent.setOnClickListener {
            val Student = Intent(applicationContext,Student::class.java)
            startActivity(Student)
        }
    }
}
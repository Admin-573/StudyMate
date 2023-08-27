package com.example.studymate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Student : AppCompatActivity() {

    private lateinit var frgt_pass : TextView
    private lateinit var student_id : EditText
    private lateinit var student_email : EditText
    private lateinit var student_pass : EditText
    private lateinit var student_login : Button
    private lateinit var student_back : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        frgt_pass = findViewById(R.id.frgt_pass_student)
        student_id = findViewById(R.id.EdtStudentName)
        student_email = findViewById(R.id.EdtStudentEmail)
        student_pass = findViewById(R.id.EdtStudentPass)
        student_login = findViewById(R.id.btnStudent_login)
        student_back = findViewById(R.id.btnBack)

        frgt_pass.setOnClickListener{
            Toast.makeText(this,"Please Contact Admin", Toast.LENGTH_SHORT).show()
        }

        student_login.setOnClickListener {
            if (validation_student())
            {
                val Faculty_panel = Intent(applicationContext,Student_panel::class.java)
                startActivity(Faculty_panel)
            }else{
                Toast.makeText(this,"Something Went Wrong ⚠️", Toast.LENGTH_SHORT).show()
            }
        }

        student_back.setOnClickListener {
            onBackPressed()
        }
    }
    private fun validation_student(): Boolean {
        if(student_id.length()==0){
            student_id.setError("Student ID Is Empty")
            return false
        }else if(student_email.length()==0){
            student_email.setError("Student Email Required")
            return false
        }else if(student_pass.length()==0){
            student_pass.setError("Student Password Required")
            return false
        }
        return true
    }
}
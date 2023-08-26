package com.example.studymate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

private lateinit var frgt_pass : TextView
private lateinit var faculty_id : EditText
private lateinit var faculty_email : EditText
private lateinit var faculty_pass : EditText
private lateinit var faculty_login : Button
private lateinit var faculty_back : Button

class Faculty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty)

        frgt_pass = findViewById(R.id.frgt_pass_faculty)
        faculty_id = findViewById(R.id.EdtFacultyNo)
        faculty_email = findViewById(R.id.EdtFacultyEmail)
        faculty_pass = findViewById(R.id.EdtFacultyPass)
        faculty_login = findViewById(R.id.btnFaculty_login)
        faculty_back = findViewById(R.id.btnBack)

        frgt_pass.setOnClickListener{
            Toast.makeText(this,"Please Contact Admin",Toast.LENGTH_SHORT).show()
        }

        faculty_login.setOnClickListener {
            if (validation_faculty())
            {
                val Faculty_panel = Intent(applicationContext,Faculty_panel::class.java)
                startActivity(Faculty_panel)
            }else{
                Toast.makeText(this,"Something Went Wrong ⚠️",Toast.LENGTH_SHORT).show()
            }
        }

        faculty_back.setOnClickListener {
            onBackPressed()
        }
    }
    private fun validation_faculty(): Boolean {
        if(faculty_id.length()==0){
            faculty_id.setError("Faculty ID Is Empty")
            return false
        }else if(faculty_email.length()==0){
            faculty_email.setError("Faculty Email Required")
            return false
        }else if(faculty_pass.length()==0){
            faculty_pass.setError("Faculty Password Required")
            return false
        }
        return true
    }
}
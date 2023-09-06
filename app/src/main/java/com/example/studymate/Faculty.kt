package com.example.studymate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.studymate.database.SQLiteHelper


private lateinit var email : EditText
private lateinit var btnNext : Button
private lateinit var btnBack : Button
private lateinit var sqLiteHelper: SQLiteHelper

class Faculty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty)

        email = findViewById(R.id.EdtFacultyEmail)
        btnNext = findViewById(R.id.btnCheck)
        btnBack = findViewById(R.id.btnBack)
        sqLiteHelper = SQLiteHelper(this)

        btnNext.setOnClickListener {
            if (validation_faculty()){
                val faculty = sqLiteHelper.isFaculty(email.text.toString().uppercase())
                if(faculty.isNotEmpty()){
                    Toast.makeText(this,"Faculty",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,Student::class.java).putExtra("faculty_email", email.text.toString().uppercase()))
                }else if(sqLiteHelper.isStudent(email.text.toString().uppercase()).isNotEmpty()){
                    Toast.makeText(this,"Student",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,Student::class.java).putExtra("student_email",
                        email.text.toString().uppercase()))
                }else{
                    Toast.makeText(this,"User not found ",Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }

        onBackPressedDispatcher.addCallback {}
    }
    private fun validation_faculty(): Boolean {
        if(email.length()==0){
            email.setError("Faculty email required")
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            Toast.makeText(this,"Enter correct email",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
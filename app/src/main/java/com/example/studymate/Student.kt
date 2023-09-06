package com.example.studymate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import com.example.studymate.database.SQLiteHelper
import com.example.studymate.faculty.Faculty_panel
import com.example.studymate.student.Student_panel

class Student : AppCompatActivity() {

    private lateinit var userId : EditText
    private lateinit var userPasswd : EditText
    private lateinit var userLogin : Button
    private lateinit var userBack : Button
    private lateinit var  sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        userId = findViewById(R.id.EdtUserId)
        userPasswd = findViewById(R.id.EdtUserPassword)
        userLogin = findViewById(R.id.userLogin)
        userBack = findViewById(R.id.userBack)

        sqLiteHelper = SQLiteHelper(this)

        val student = intent.getStringExtra("student_email")
        val faculty = intent.getStringExtra("faculty_email")

        if (student != null) {
            val yesIsStudent = sqLiteHelper.isStudent(student)
            userId.setText(yesIsStudent[0].student_id.toString())
        }

        if (faculty != null) {
            val yesIsFaculty = sqLiteHelper.isFaculty(faculty)
            userId.setText(yesIsFaculty[0].faculty_id.toString())
        }

        val id = userId.text.toString()

        userLogin.setOnClickListener {
            if (validation_student()) {
                val valFac = sqLiteHelper.chkPasswdFaculty(id.toInt())
                if (valFac.isNotEmpty()) {
                    if (userPasswd.text.toString() == valFac[0].faculty_password) {
                        showToast("Login Successfully !")
                        startActivity(Intent(applicationContext, Faculty_panel::class.java))
                        userId.text.clear()
                        userPasswd.text.clear()
                    } else {
                        showToast("Incorrect Password !")
                    }
                }else if (sqLiteHelper.chkPasswdStudent(id.toInt()).isNotEmpty()) {
                    if (userPasswd.text.toString() == sqLiteHelper.chkPasswdStudent(id.toInt())[0].student_password) {
                        showToast("Login Successfully !")
                        startActivity(Intent(applicationContext, Student_panel::class.java))
                        userId.text.clear()
                        userPasswd.text.clear()
                    } else {
                        showToast("Incorrect Password !")
                    }
                }
            }
        }

        userBack.setOnClickListener {
            startActivity(Intent(applicationContext, Faculty::class.java))
        }

        onBackPressedDispatcher.addCallback {}
    }

    private fun validation_student(): Boolean {
        if(userPasswd.length()==0){
            userPasswd.setError("Password cann't be empty")
            return false
        }
        return true
    }

    private fun showToast(str : String){
        Toast.makeText(applicationContext,str,Toast.LENGTH_SHORT).show()
    }
}
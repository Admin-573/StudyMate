package com.example.studymate.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper

class student_add : AppCompatActivity() {
    private lateinit var student_name : EditText
    private lateinit var student_email : EditText
    private lateinit var student_password : EditText
    private lateinit var student_class : EditText

    private lateinit var btn_add_student : Button
    private lateinit var btnBack : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter?= null
    private var adm : AdminModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_add)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        btn_add_student.setOnClickListener {
            if(studentValidation()){
                addStudent()
                clearFields()
                startActivity(Intent(applicationContext, student_view::class.java))
            }
        }

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun studentValidation(): Boolean {
        if(student_name.length() == 0){
            student_name.setError("Name Required")
            return false
        } else if(student_email.length()==0){
            student_email.setError("Email Can't Be Empty")
            return false
        } else if(student_password.length()==0) {
            student_password.setError("Password Required")
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(student_email.text.toString()).matches()){
            Toast.makeText(this,"Email Format Is Wrong !",Toast.LENGTH_SHORT).show()
            return false
        } else if(student_class.length()==0) {
            student_class.setError("Class Needed")
            return false
        }
        return true
    }

    private fun addStudent() {
        val name = student_name.text.toString()
        val email = student_email.text.toString()
        val pass = student_password.text.toString()
        val stud_class = student_class.text.toString()
        val adm = AdminModel(student_name = name, student_email = email, student_password = pass, student_class = stud_class)
        val status = sqLiteHelper.InsertStudent(adm)
        if(status > -1){
            Toast.makeText(this,"Student Added",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Student Exists",Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        student_name.setText("")
        student_email.setText("")
        student_password.setText("")
        student_class.setText("")
        student_name.requestFocus()
    }

    private fun initView() {
        student_name = findViewById(R.id.Admin_add_Student_name)
        student_email = findViewById(R.id.Admin_add_student_email)
        student_password = findViewById(R.id.Admin_add_student_pass)
        student_class = findViewById(R.id.Admin_add_student_class)
        btn_add_student = findViewById(R.id.btnAdmin_add_student)
        btnBack = findViewById(R.id.btnBack)
    }
}
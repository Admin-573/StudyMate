package com.example.studymate.faculty

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

class faculty_add : AppCompatActivity() {

    private lateinit var faculty_name : EditText
    private lateinit var faculty_email : EditText
    private lateinit var faculty_password : EditText
    private lateinit var faculty_sub : EditText
    private lateinit var btn_add_faculty : Button
    private lateinit var btn_back : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : FacultyAdapter?= null
    private var adm : AdminModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_add)
        initView()
        sqLiteHelper = SQLiteHelper(this)
        btn_add_faculty.setOnClickListener {
            if(faculty_validation()){
                addFaculty()
                clearFaculty()
                Toast.makeText(this,"Checkout 🎯",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, faculty_view::class.java))
            }
        }
        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun clearFaculty() {
        faculty_name.setText("")
        faculty_email.setText("")
        faculty_password.setText("")
        faculty_sub.setText("")
        faculty_name.requestFocus()
    }

    private fun addFaculty() {
        val name = faculty_name.text.toString()
        val email = faculty_email.text.toString()
        val pass = faculty_password.text.toString()
        val sub = faculty_sub.text.toString()
        val adm = AdminModel(faculty_name = name, faculty_email = email, faculty_password = pass, faculty_sub = sub)
        val status = sqLiteHelper.InsertFaculty(adm)
        if(status > -1){
            Toast.makeText(this,"Faculty Added",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Faculty Exists",Toast.LENGTH_SHORT).show()
        }
    }

    private fun faculty_validation(): Boolean {
        if(faculty_name.length() == 0){
            faculty_name.setError("Name Required")
            return false
        } else if(faculty_email.length()==0) {
            faculty_email.setError("Email Can't Be Empty")
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(faculty_email.text.toString()).matches()){
            Toast.makeText(this,"Email Format Wrong !",Toast.LENGTH_SHORT).show()
            return false
        } else if(faculty_password.length()==0){
            faculty_password.setError("Password Required")
            return false
        } else if(faculty_sub.length()==0) {
            faculty_sub.setError("Subject Needed")
            return false
        }
        return true
    }

    private fun initView() {
        faculty_name = findViewById(R.id.Admin_add_faculty_name)
        faculty_email = findViewById(R.id.Admin_add_faculty_email)
        faculty_password = findViewById(R.id.Admin_add_faculty_pass)
        faculty_sub = findViewById(R.id.Admin_add_faculty_sub)
        btn_add_faculty = findViewById(R.id.btnAdmin_add_faculty)
        btn_back = findViewById(R.id.btnBack)
    }
}
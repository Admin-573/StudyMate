package com.example.studymate.faculty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.studymate.R
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper

class faculty_update : AppCompatActivity() {

    private lateinit var upd_name : EditText
    private lateinit var upd_email : EditText
    private lateinit var upd_password : EditText
    private lateinit var upd_sub : EditText
    private lateinit var btn_upd : Button
    private lateinit var btn_back : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter : FacultyAdapter?= null
    private var adm : AdminModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_update)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        //Getting Data From View For Update
        upd_name.setText(intent.getStringExtra("faculty_name"))
        upd_email.setText(intent.getStringExtra("faculty_email"))
        upd_password.setText(intent.getStringExtra("faculty_pass"))
        upd_sub.setText(intent.getStringExtra("faculty_sub"))

        btn_upd.setOnClickListener {
            if(validation()){
                updateFaculty()
            } else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validation(): Boolean {
        if(upd_name.length() == 0){
            upd_name.setError("Name Required")
            return false
        } else if(upd_password.length()==0){
            upd_password.setError("Password Not Be Null")
            return false
        } else if(upd_sub.length()==0){
            upd_sub.setError("Subject Can't Be Empty")
            return false
        }
        return true
    }

    private fun updateFaculty() {
        val faculty = AdminModel( faculty_name = upd_name.text.toString() , faculty_email = upd_email.text.toString(), faculty_sub = upd_sub.text.toString(), faculty_password = upd_password.text.toString())
        val rc =  sqLiteHelper.updateFacultyByEmail(faculty)
        if(rc > 0){
            getFaculty()
            Toast.makeText(applicationContext,"Update",Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, faculty_view::class.java))
        }else{
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFaculty() {
        val admList = sqLiteHelper.getAllFaculty()
        adapter?.addItems(admList)
    }

    private fun initView() {
        upd_name = findViewById(R.id.Admin_update_faculty_name)
        upd_email = findViewById(R.id.Admin_update_faculty_email)
        upd_password = findViewById(R.id.Admin_update_faculty_pass)
        upd_sub = findViewById(R.id.Admin_update_faculty_sub)
        btn_upd = findViewById(R.id.btnAdmin_update_faculty)
        btn_back = findViewById(R.id.btnBack)
    }
}
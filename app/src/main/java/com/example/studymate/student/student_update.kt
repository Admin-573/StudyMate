package com.example.studymate.student

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.studymate.R
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper
import com.example.studymate.faculty.FacultyAdapter

class student_update : AppCompatActivity() {
    private lateinit var upd_name : EditText
    private lateinit var upd_email : EditText
    private lateinit var upd_password : EditText
    private lateinit var upd_class : EditText
    private lateinit var upd_image : ImageView
    private lateinit var upd_id:EditText
    private lateinit var btn_upd : Button
    private lateinit var btn_back : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter : FacultyAdapter?= null
    private var adm : AdminModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_update)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        //Getting Data From View For Update
        upd_id.setText(intent.getIntExtra("student_id",0).toString())
        upd_name.setText(intent.getStringExtra("student_name"))
        upd_email.setText(intent.getStringExtra("student_email"))
        upd_password.setText(intent.getStringExtra("student_pass"))
        upd_class.setText(intent.getStringExtra("student_class"))
        if (intent.getByteArrayExtra("student_image")!=null){
            upd_image.setImageBitmap(BitmapFactory.decodeByteArray(intent.getByteArrayExtra("student_image"),0,intent.getByteArrayExtra("student_image")!!.size))
        }

        btn_upd.setOnClickListener {
            if(validation()){
                updateStudent()
            } else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateStudent() {
        val student = AdminModel( student_id = upd_id.text.toString().toInt(),student_name = upd_name.text.toString() , student_email = upd_email.text.toString(), student_class = upd_class.text.toString(), student_password = upd_password.text.toString())
        val rc =  sqLiteHelper.updateStudentById(student)
        if(rc > 0){
            getStudent()
            Toast.makeText(applicationContext,"Updated",Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, student_view::class.java))
        }else{
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStudent() {
        val admList = sqLiteHelper.getAllStudent()
        adapter?.addItems(admList)
    }

    private fun validation(): Boolean {
        if(upd_name.length() == 0){
            upd_name.setError("Name Required")
            return false
        } else if(upd_password.length()==0){
            upd_password.setError("Password Not Be Null")
            return false
        } else if(upd_class.length()==0){
            upd_class.setError("Class Can't Be Empty")
            return false
        }
        return true
    }

    private fun initView() {
        upd_name = findViewById(R.id.Admin_update_student_name)
        upd_email = findViewById(R.id.Admin_update_student_email)
        upd_password = findViewById(R.id.Admin_update_student_pass)
        upd_class = findViewById(R.id.Admin_update_student_class)
        upd_image = findViewById(R.id.Admin_update_student_image)
        upd_id = findViewById(R.id.Admin_update_student_Id)
        btn_upd = findViewById(R.id.btnAdmin_update_student)
        btn_back = findViewById(R.id.btnBack)
    }
}
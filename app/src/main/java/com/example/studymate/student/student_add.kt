package com.example.studymate.student

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.studymate.R
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper
import java.io.ByteArrayOutputStream

class student_add : AppCompatActivity() {
    private lateinit var student_name : EditText
    private lateinit var student_email : EditText
    private lateinit var student_password : EditText
    private lateinit var student_class : EditText

    private lateinit var student_image : ImageView
    private lateinit var byteArray: ByteArray

    private lateinit var btn_add_student : Button
    private lateinit var btnBack : Button

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_add)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        btnBack.setOnClickListener {
            onBackPressed()
        }

        student_image.setOnClickListener {
            val  i = Intent(Intent.ACTION_GET_CONTENT)
            i.setType("image/*")
            ImageUploading.launch(i)
        }

    }

    private val ImageUploading = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        rc ->
        if (rc.resultCode == RESULT_OK) {
            val name = student_name.text.toString()
            val email = student_email.text.toString()
            val pass = student_password.text.toString()
            val sub = student_class.text.toString()
            val uri = rc.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                byteArray = byteArrayOutputStream.toByteArray()

                student_image.setImageBitmap(bitmap)
                inputStream!!.close()


                btn_add_student.setOnClickListener {
                    if (studentValidation()) {
                        val student = AdminModel(
                            student_name = name,
                            student_email = email,
                            student_password = pass,
                            student_class = sub,
                            student_image = byteArray
                        )
                        val status = sqLiteHelper.InsertStudent(student)
                        if (status > -1) {
                            Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show()
                            clearFields()
                            startActivity(Intent(applicationContext, student_view::class.java))
                        } else {
                            Toast.makeText(this, "Student Exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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
    private fun clearFields() {
        student_name.setText("")
        student_email.setText("")
        student_password.setText("")
        student_class.setText("")
        student_name.requestFocus()
        student_image.setImageDrawable(resources.getDrawable(R.drawable.name))
    }

    private fun initView() {
        student_name = findViewById(R.id.Admin_add_Student_name)
        student_email = findViewById(R.id.Admin_add_student_email)
        student_password = findViewById(R.id.Admin_add_student_pass)
        student_class = findViewById(R.id.Admin_add_student_class)
        btn_add_student = findViewById(R.id.btnAdmin_add_student)
        student_image = findViewById(R.id.student_image)
        btnBack = findViewById(R.id.btnBack)
    }
}
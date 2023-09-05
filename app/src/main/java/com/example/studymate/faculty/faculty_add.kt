package com.example.studymate.faculty

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
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

class faculty_add : AppCompatActivity() {

    private lateinit var faculty_name : EditText
    private lateinit var faculty_email : EditText
    private lateinit var faculty_password : EditText
    private lateinit var faculty_sub : EditText

    private lateinit var faculty_image : ImageView
    private lateinit var byteArray: ByteArray

    private lateinit var btn_add_faculty : Button
    private lateinit var btn_back : Button

    private val FAC_ID : Int = (2100000..2200000).random()

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_add)
        initView()
        sqLiteHelper = SQLiteHelper(this)
        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_add_faculty.setOnClickListener {
            if(faculty_validation()){
                addFaculty()
            }
        }

        faculty_image.setOnClickListener {
            val  i = Intent(Intent.ACTION_GET_CONTENT)
            i.setType("image/*")
            ImageUploading.launch(i)
        }
    }

    private fun addFaculty() {

        val name = faculty_name.text.toString()
        val email = faculty_email.text.toString()
        val pass = faculty_password.text.toString()
        val sub = faculty_sub.text.toString()

        val faculty = AdminModel(
            faculty_id = FAC_ID,
            faculty_image = byteArray,
            faculty_name = name,
            faculty_email = email,
            faculty_password = pass,
            faculty_sub = sub
        )

        val addFacultyRecord = sqLiteHelper.InsertFaculty(faculty)

        if(addFacultyRecord > -1){
            Log.d("icmain",addFacultyRecord.toString())
            Log.d("icmain",faculty.faculty_id.toString())
            Log.d("icmain",faculty.faculty_image.toString())

            Toast.makeText(applicationContext,"Faculty Added Successfully",Toast.LENGTH_SHORT).show()
            clearFaculty()
            finish()
        }else{
            Log.d("icmain",addFacultyRecord.toString())
            Toast.makeText(applicationContext,"Faculty Exists",Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFaculty() {
        faculty_name.setText("")
        faculty_email.setText("")
        faculty_password.setText("")
        faculty_sub.setText("")
        faculty_name.requestFocus()
        faculty_image.setImageDrawable(resources.getDrawable(R.drawable.add_img))
    }

    @SuppressLint("SuspiciousIndentation")
    private val ImageUploading = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        rc -> if(rc.resultCode == RESULT_OK){
            val uri = rc.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                if(inputStream!=null) {
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    byteArray = byteArrayOutputStream.toByteArray()
                        if (byteArray.size / 1024 < 600) {
                            faculty_image.setImageBitmap(bitmap)
                            inputStream.close()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Please choose image below 600K",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }else{
                    Toast.makeText(this, "Input Stream Null", Toast.LENGTH_SHORT).show()
                }
            }catch (e : Exception){
                    e.printStackTrace()
            }
        }
    }
    //don't panic if u can't see a validation
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
        }else if(!faculty_image.isSelected){
            Toast.makeText(applicationContext,"Please Upload image !",Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun initView() {
        faculty_name = findViewById(R.id.Admin_add_faculty_name)
        faculty_email = findViewById(R.id.Admin_add_faculty_email)
        faculty_password = findViewById(R.id.Admin_add_faculty_pass)
        faculty_sub = findViewById(R.id.Admin_add_faculty_sub)
        faculty_image = findViewById(R.id.faculty_image)
        btn_add_faculty = findViewById(R.id.btnAdmin_add_faculty)
        btn_back = findViewById(R.id.btnBack)
    }
}
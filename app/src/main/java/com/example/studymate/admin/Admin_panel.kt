package com.example.studymate.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studymate.AboutUs
import com.example.studymate.Admin
import com.example.studymate.ContactUs
import com.example.studymate.R
import com.example.studymate.assignment.assignment_add
import com.example.studymate.assignment.assignment_view
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper
import com.example.studymate.faculty.faculty_add
import com.example.studymate.faculty.faculty_view
import com.example.studymate.notice.notice_add
import com.example.studymate.notice.notice_view
import com.example.studymate.student.student_add
import com.example.studymate.student.student_view
import com.google.android.material.navigation.NavigationView
import java.io.ByteArrayOutputStream

class Admin_panel : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var add_faculty : LinearLayout
    private lateinit var add_student : LinearLayout
    private lateinit var add_notice : LinearLayout
    private lateinit var add_assignment : LinearLayout
    private lateinit var admin_aboutus : LinearLayout
    private lateinit var admin_contactus : LinearLayout
    private lateinit var byteArray: ByteArray
    private lateinit var adminSession: AdminSession
    private lateinit var  sqLiteHelper: SQLiteHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        sqLiteHelper = SQLiteHelper(this)

        adminSession= AdminSession(this)

        //Navigation Drawer
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val view : View = navView.getHeaderView(0)

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        val image : ImageView = view.findViewById(R.id.admin_photo)
        val name : TextView = view.findViewById(R.id.admin_name_head)
        val email : TextView = view.findViewById(R.id.admin_email_head)

        image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            ActivityLauncher.launch(intent)
        }

        name.setText(adminSession.sharedPreferences.getString("name",""))
        email.setText(adminSession.sharedPreferences.getString("email",""))
        val adminEmail = adminSession.sharedPreferences.getString("email","")
        if(adminEmail!=null){
            if(sqLiteHelper.checkImage(adminEmail)){
                //GetImage()
            }
        }

        //BackPressed CallBack
        onBackPressedDispatcher.addCallback{
            Toast.makeText(applicationContext,"Please Logout To GoBack",Toast.LENGTH_SHORT).show()
        }
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.admin_nav_profile -> Toast.makeText(this,"You Are On Profile",Toast.LENGTH_SHORT).show()
                R.id.admin_nav_addfaculty -> {
                    val faculty_add = Intent(applicationContext, faculty_add::class.java)
                    startActivity(faculty_add)
                }
                R.id.admin_nav_addstudent -> {
                    val student_add = Intent(applicationContext, student_add::class.java)
                    startActivity(student_add)
                }
                R.id.admin_nav_notices -> {
                    val notice_add = Intent(applicationContext, notice_add::class.java)
                    startActivity(notice_add)
                }
                R.id.admin_nav_assignments -> {
                    val assignment_add = Intent(applicationContext, assignment_add::class.java)
                    startActivity(assignment_add)
                }
                R.id.admin_nav_logout -> {
                    adminSession.adminLogout()
                    startActivity(Intent(applicationContext,Admin::class.java))
                    finish()
                }
                R.id.admin_nav_contactUs -> {
                    startActivity(Intent(applicationContext,ContactUs::class.java))
                }
                R.id.admin_nav_aboutUs -> {
                    startActivity(Intent(applicationContext,AboutUs::class.java))
                }
            }
            true
        }

        //Admin Adds Faculty
        add_faculty = findViewById(R.id.admin_view_faculty)
        add_faculty.setOnClickListener{
            startActivity(Intent(applicationContext, faculty_view::class.java))
        }

        //Admin Adds Student
        add_student = findViewById(R.id.admin_view_student)
        add_student.setOnClickListener{
            startActivity(Intent(applicationContext, student_view::class.java))
        }

        //Admin Adds Notices
        add_notice = findViewById(R.id.admin_view_notice)
        add_notice.setOnClickListener{
            startActivity(Intent(applicationContext, notice_view::class.java))
        }

        //Admin Adds Assignments
        add_assignment = findViewById(R.id.admin_view_assignment)
        add_assignment.setOnClickListener{
            startActivity(Intent(applicationContext, assignment_view::class.java))
        }

        //Admin AboutUS
        admin_aboutus = findViewById(R.id.admin_about_us)
        admin_aboutus.setOnClickListener{
            startActivity(Intent(applicationContext,AboutUs::class.java))
        }

        //Admin ContactUS
        admin_contactus = findViewById(R.id.admin_contact_us)
        admin_contactus.setOnClickListener{
            startActivity(Intent(applicationContext,ContactUs::class.java))
        }

    }

    private fun GetImage(){
        val adminEmail = adminSession.sharedPreferences.getString("email","")
        val navView : NavigationView = findViewById(R.id.nav_view)
        val view : View = navView.getHeaderView(0)
        val image : ImageView = view.findViewById(R.id.admin_photo)
        if(adminEmail!=null){
            Toast.makeText(applicationContext,adminEmail.toString(),Toast.LENGTH_SHORT).show()
            val admin = sqLiteHelper.getAdminImage(adminEmail)
            if(admin.isNotEmpty()){
                val byteArrayOutputStream = ByteArrayOutputStream()
                BitmapFactory.decodeByteArray(admin[0].admin_image,0,admin[0].admin_image!!.size)
                    .compress(Bitmap.CompressFormat.PNG,20,byteArrayOutputStream)
                val byte = byteArrayOutputStream.toByteArray()
                val bitmap = BitmapFactory.decodeByteArray(byte,0,byte.size)
                image.setImageBitmap(bitmap)
            }
        }
    }

    private val ActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Activityresult ->
        if(Activityresult.resultCode == RESULT_OK){
            val uri = Activityresult.data!!.data
            val navView : NavigationView = findViewById(R.id.nav_view)
            val view : View = navView.getHeaderView(0)
            val image : ImageView = view.findViewById(R.id.admin_photo)
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
                byteArray = stream.toByteArray()


                image.setImageBitmap(bitmap)
                inputStream!!.close()


                val email : String  =  adminSession.sharedPreferences.getString("email","").toString()
                Log.d("rc-mail",email)
                val images =  byteArray

                val adminModel = AdminModel(admin_email = email , admin_image = images)
                Log.d("rc-model",adminModel.toString())
                val ic = sqLiteHelper.updateImage(adminModel)
                Log.d("rc-query",ic.toString())

                if(ic  > -1){
                    Toast.makeText(applicationContext,"RecordUpdate",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"Not Update",Toast.LENGTH_SHORT).show()
                }


            }catch (e : Exception){
                Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Navigation Drawer OnSelect Event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
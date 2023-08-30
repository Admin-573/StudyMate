package com.example.studymate.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.studymate.Admin
import com.example.studymate.R
import com.example.studymate.assignment.assignment_add
import com.example.studymate.assignment.assignment_view
import com.example.studymate.faculty.faculty_add
import com.example.studymate.faculty.faculty_view
import com.example.studymate.notice.notice_add
import com.example.studymate.notice.notice_view
import com.example.studymate.student.student_add
import com.example.studymate.student.student_view
import com.google.android.material.navigation.NavigationView

class Admin_panel : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var add_faculty : LinearLayout
    private lateinit var add_student : LinearLayout
    private lateinit var add_notice : LinearLayout
    private lateinit var add_assignment : LinearLayout
    private lateinit var admin_aboutus : LinearLayout
    private lateinit var admin_contactus : LinearLayout

    private lateinit var adminSession: AdminSession

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        adminSession= AdminSession(this)

        //Navigation Drawer
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        val view : View = navView.getHeaderView(0)
        val name : TextView = view.findViewById(R.id.admin_name_head)
        val email : TextView = view.findViewById(R.id.admin_email_head)

        name.setText(adminSession.sharedPreferences.getString("name",""))
        email.setText(adminSession.sharedPreferences.getString("email",""))

        //BackPressed CallBack
        onBackPressedDispatcher.addCallback{
            Toast.makeText(applicationContext,"User Login, back pressed 🔙",Toast.LENGTH_SHORT).show()
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
                R.id.admin_nav_contactUs -> Toast.makeText(this,"Contact Us",Toast.LENGTH_SHORT).show()
                R.id.admin_nav_aboutUs -> Toast.makeText(this,"About Us",Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this,"Admin About US",Toast.LENGTH_SHORT).show()
        }

        //Admin ContactUS
        admin_contactus = findViewById(R.id.admin_contact_us)
        admin_contactus.setOnClickListener{
            Toast.makeText(this,"Admin Contact US",Toast.LENGTH_SHORT).show()
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
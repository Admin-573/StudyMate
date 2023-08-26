package com.example.studymate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Admin : AppCompatActivity() {

    private lateinit var admin_org_no : EditText
    private lateinit var admin_name : EditText
    private lateinit var admin_email : EditText

    private lateinit var admin_login: Button
    private lateinit var admin_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        init_call()

        admin_login.setOnClickListener {
            validation_admin()
        }
        admin_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun validation_admin() : Boolean{
        if(admin_org_no.length() == 0){
            admin_org_no.setError("Security ID Required")
            return false
        } else if(admin_name.length()==0){
            admin_name.setError("Name Can't Be Empty")
            return false
        } else if(admin_email.length()==0){
            admin_email.setError("Email ID Required")
            return false
        } else {
            if (admin_org_no.text.toString() == "India@123"){
                val Admin_panel = Intent(applicationContext,Admin_panel::class.java)
                startActivity(Admin_panel)
            } else {
                admin_org_no.setError("Security ID Wrong")
            }
        }
        return true
    }

    private fun init_call() {
        admin_org_no = findViewById(R.id.EdtOrgNo)
        admin_name = findViewById(R.id.EdtAdminName)
        admin_email = findViewById(R.id.EdtAdminEmail)
        admin_login = findViewById(R.id.btnAdmin_login)
        admin_back = findViewById(R.id.btnBack)
    }
}
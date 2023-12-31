package com.example.studymate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.admin.AdminSession
import com.example.studymate.database.AdminModel
import com.example.studymate.admin.Admin_panel
import com.example.studymate.database.SQLiteHelper

class Admin : AppCompatActivity() {

    private lateinit var admin_org_no : EditText
    private lateinit var admin_name : EditText
    private lateinit var admin_email : EditText

    private lateinit var admin_login: Button
    private lateinit var admin_back: Button

    private lateinit var adminSession: AdminSession

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adm: AdminModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        init_call()
        sqLiteHelper = SQLiteHelper(this)

        adminSession = AdminSession(this)

        admin_login.setOnClickListener {
            if(validation_admin()){
                addAdmin()
                clearFields()
            }
        }
        admin_back.setOnClickListener {
            onBackPressed()
        }

        //if Admin is login then it'll redirected to admin_panel
        if(adminSession.login()){
            startActivity(Intent(applicationContext,Admin_panel::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }
    }

    private fun clearFields() {
        admin_org_no.setText("")
        admin_name.setText("")
        admin_email.setText("")
        admin_org_no.requestFocus()
    }

    //Adding Admin Data To DB
    private fun addAdmin() {
        val name = admin_name.text.toString()
        val email = admin_email.text.toString()
        val adm = AdminModel(admin_name = name, admin_email = email)
        val status = sqLiteHelper.InsertAdmin(adm)
        if(status > -1)
        {
            adminSession.adminLogin(email,name)
            Toast.makeText(this,"Admin Logged In",Toast.LENGTH_SHORT).show()
            finish()
        }
        else
        {
            adminSession.adminLogin(email,name)
            Toast.makeText(this,"Admin Founded Good To Go ~",Toast.LENGTH_SHORT).show()
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
        } else if(!Patterns.EMAIL_ADDRESS.matcher(admin_email.text.toString()).matches()){
            Toast.makeText(this,"Email Format  Wrong !",Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (admin_org_no.text.toString() == "India@123"
                || admin_org_no.text.toString() == "india@123"
                || admin_org_no.text.toString() == "INDIA@123"){
                val Admin_panel = Intent(applicationContext, Admin_panel::class.java)
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
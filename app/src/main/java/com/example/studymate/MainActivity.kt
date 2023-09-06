package com.example.studymate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

@SuppressLint("MissingInflatedId")
class MainActivity : AppCompatActivity() {

    private lateinit var btnAdmin: Button
    private lateinit var btnOther: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdmin = findViewById(R.id.btnAdmin)
        btnOther = findViewById(R.id.btnOther)

        btnAdmin.setOnClickListener {
            val Admin = Intent(applicationContext, Admin::class.java)
            startActivity(Admin)
        }
        btnOther.setOnClickListener {
            startActivity(Intent(applicationContext,Faculty::class.java))
        }
    }
}
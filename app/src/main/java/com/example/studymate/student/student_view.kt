package com.example.studymate.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper

class student_view : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : StudentAdapter?= null
    private var adm : AdminModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_view)

        sqLiteHelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqLiteHelper.getAllStudent()
        adapter?.addItems(admList)

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.student_email)
        }

        adapter?.setOnClickItem{
            Log.d("checkSid",it.student_id.toString())
            startActivity(
                Intent(applicationContext, student_update::class.java)
                    .putExtra("student_id",it.student_id)
                    .putExtra("student_image",it.student_image)
                    .putExtra("student_name",it.student_name)
                    .putExtra("student_email",it.student_email)
                    .putExtra("student_pass",it.student_password)
                    .putExtra("student_class",it.student_class)
            )
        }
    }

    private fun deleteStudent(studentEmail: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do You Want To Delete This Student ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_->
            sqLiteHelper.DeleteStudent(studentEmail)
            getStudent()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog,_->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun getStudent() {
        val admList = sqLiteHelper.getAllStudent()
        adapter?.addItems(admList)
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewStudent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }
}
package com.example.studymate.faculty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper

class faculty_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : FacultyAdapter?= null
    private var adm : AdminModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_view)

        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllFaculty()
        adapter?.addItems(admList)

        adapter?.setOnClickDeleteItem {
            deleteFaculty(it.faculty_email)
        }

        adapter?.setOnClickItem{
            Toast.makeText(this,it.faculty_name,Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, faculty_update::class.java)
                .putExtra("faculty_name",it.faculty_name)
                .putExtra("faculty_email",it.faculty_email)
                .putExtra("faculty_pass",it.faculty_password)
                .putExtra("faculty_sub",it.faculty_sub))
        }
    }
    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewFaculty)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FacultyAdapter()
        recyclerView.adapter = adapter
    }

    private fun deleteFaculty(email: String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do You Want To Delete This Faculty ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_->
            sqlitehelper.DeleteFaculty(email)
            getFaculty()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog,_->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()

    }

    private fun getFaculty() {
        val admList = sqlitehelper.getAllFaculty()
        adapter?.addItems(admList)
    }
}
package com.example.studymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class faculty_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : FacultyAdapter ?= null
    private var adm : AdminModel ?= null
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
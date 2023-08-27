package com.example.studymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class assignment_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : AssignmentAdapter ?= null
    private var adm : AdminModel ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment_view)

        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllAssignment()
        adapter?.addItems(admList)

        adapter?.setOnClickDeleteItem {
            deleteAssignment(it.assignment_name)
        }

        adapter?.setOnClickItem{
            Toast.makeText(this,it.assignment_name,Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteAssignment(name_assign: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do You Want To Delete This Assignment ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_->
            sqlitehelper.DeleteAssignment(name_assign)
            getAssignment()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog,_->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun getAssignment() {
        val admList = sqlitehelper.getAllAssignment()
        adapter?.addItems(admList)
    }


    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewAssignment)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AssignmentAdapter()
        recyclerView.adapter = adapter
    }
}
package com.example.studymate.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.admin.Admin_panel
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper
import com.example.studymate.databinding.ActivityAssignmentViewBinding

class assignment_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : AssignmentAdapter?= null
    private lateinit var binding : ActivityAssignmentViewBinding
    private var adm : AdminModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllAssignment()
        adapter?.addItems(admList)

        adapter?.setOnClickItem{
            Toast.makeText(this,"Submit On : ${it.assignment_sdate}",Toast.LENGTH_SHORT).show()
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }
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
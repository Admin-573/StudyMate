package com.example.studymate.faculty

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
import com.example.studymate.databinding.ActivityFacultyViewBinding

class faculty_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : FacultyAdapter?= null
    private var adm : AdminModel?= null

    private lateinit var binding : ActivityFacultyViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllFaculty()
        adapter?.addItems(admList)

        adapter?.setOnClickItem{
            startActivity(Intent(applicationContext, faculty_update::class.java)
                .putExtra("faculty_id",it.faculty_id)
                .putExtra("faculty_image",it.faculty_image)
                .putExtra("faculty_name",it.faculty_name)
                .putExtra("faculty_email",it.faculty_email)
                .putExtra("faculty_pass",it.faculty_password)
                .putExtra("faculty_sub",it.faculty_sub))
        }


        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }

    }
    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewFaculty)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FacultyAdapter()
        recyclerView.adapter = adapter
    }

    private fun getFaculty() {
        val admList = sqlitehelper.getAllFaculty()
        adapter?.addItems(admList)
    }
}
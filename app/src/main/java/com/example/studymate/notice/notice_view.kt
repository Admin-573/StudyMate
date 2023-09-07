package com.example.studymate.notice

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
import com.example.studymate.databinding.ActivityNoticeViewBinding

class notice_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : NoticeAdapter?= null

    private lateinit var binding : ActivityNoticeViewBinding

    private var adm : AdminModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllNotice()
        adapter?.addItems(admList)

        adapter?.setOnClickItem{
            Toast.makeText(this,it.notice_name,Toast.LENGTH_SHORT).show()
        }


        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }
    }

    //we are not deleting notice
    private fun deleteNotice(name: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do You Want To Delete This Notice ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_->
            sqlitehelper.DeleteNotice(name)
            getNotice()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog,_->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun getNotice() {
        val admList = sqlitehelper.getAllNotice()
        adapter?.addItems(admList)
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewNotice)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoticeAdapter()
        recyclerView.adapter = adapter
    }
}
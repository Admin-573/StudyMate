package com.example.studymate.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.database.AdminModel
import com.example.studymate.database.SQLiteHelper

class notice_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : NoticeAdapter?= null
    private var adm : AdminModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_view)

        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllNotice()
        adapter?.addItems(admList)

        adapter?.setOnClickDeleteItem {
            deleteNotice(it.notice_name)
        }

        adapter?.setOnClickItem{
            Toast.makeText(this,it.notice_name,Toast.LENGTH_SHORT).show()
        }
    }

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
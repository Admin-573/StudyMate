package com.example.studymate.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.database.AdminModel

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    private var admList : ArrayList<AdminModel> = ArrayList()
    private var onClickItem : ((AdminModel) -> Unit) ?= null
    private var onClickDeleteItem : ((AdminModel) -> Unit) ?= null

    fun addItems(items: ArrayList<AdminModel>){
        this.admList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((AdminModel) -> Unit) ?= null){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: ((AdminModel) -> Unit)){
        this.onClickDeleteItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= NoticeViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_notice_data,parent,false)
    )

    override fun getItemCount(): Int {
        return admList.size
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val adm = admList[position]
        holder.bindView(adm)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(adm)
        }
        holder.btnDelete.setOnClickListener {
            onClickDeleteItem?.invoke(adm)
        }
    }

    class NoticeViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.view_notice_name)
        private var des = view.findViewById<TextView>(R.id.view_notice_des)
        private var date = view.findViewById<TextView>(R.id.view_notice_date)
        var btnDelete = view.findViewById<Button>(R.id.btnDeleteNotice)

        fun bindView(adm : AdminModel){
            name.text = adm.notice_name
            des.text = adm.notice_des
            date.text = adm.notice_date
        }
    }
}
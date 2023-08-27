package com.example.studymate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_student_data,parent,false)
    )

    override fun getItemCount(): Int {
        return admList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val adm = admList[position]
        holder.bindView(adm)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(adm)
        }
        holder.btnDelete.setOnClickListener {
            onClickDeleteItem?.invoke(adm)
        }
    }

    class StudentViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.student_view_name)
        private var email = view.findViewById<TextView>(R.id.student_view_email)
        private var sub = view.findViewById<TextView>(R.id.student_view_sub)
        var btnDelete = view.findViewById<Button>(R.id.btnDeleteStudent)

        fun bindView(adm : AdminModel){
            name.text = adm.student_name
            email.text = adm.student_email
            sub.text = adm.student_class
        }
    }
}
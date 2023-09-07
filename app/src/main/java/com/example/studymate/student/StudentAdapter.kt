package com.example.studymate.student

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studymate.R
import com.example.studymate.database.AdminModel

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var admList : ArrayList<AdminModel> = ArrayList()
    private var onClickItem : ((AdminModel) -> Unit) ?= null

    fun addItems(items: ArrayList<AdminModel>){
        this.admList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: ((AdminModel) -> Unit) ?= null){
        this.onClickItem = callback
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
    }

    class StudentViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.student_view_name)
        private var email = view.findViewById<TextView>(R.id.student_view_email)
        private var image = view.findViewById<ImageView>(R.id.student_view_image)

        fun bindView(adm : AdminModel){
            name.text = adm.student_name
            email.text = adm.student_email
            if (adm.student_image!=null){
                image.setImageBitmap(BitmapFactory.decodeByteArray(adm.student_image,0,adm.student_image!!.size))
            }
        }
    }
}
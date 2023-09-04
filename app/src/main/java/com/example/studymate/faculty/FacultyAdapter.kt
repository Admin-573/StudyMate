package com.example.studymate.faculty

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
import com.example.studymate.faculty.FacultyAdapter.FacultyViewHolder

class FacultyAdapter : RecyclerView.Adapter<FacultyViewHolder>() {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= FacultyViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_faculty_data,parent,false)
    )

    override fun getItemCount(): Int {
        return admList.size
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        val adm = admList[position]
        holder.bindView(adm)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(adm)
        }
        holder.btnDelete.setOnClickListener {
            onClickDeleteItem?.invoke(adm)
        }
    }

    class FacultyViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.faculty_view_name)
        private var email = view.findViewById<TextView>(R.id.faculty_view_email)
        private var sub = view.findViewById<TextView>(R.id.faculty_view_sub)
        private var image = view .findViewById<ImageView>(R.id.faculty_view_image)
        var btnDelete = view.findViewById<Button>(R.id.btnDeleteFaculty)

        fun bindView(adm : AdminModel){
            name.text = adm.faculty_name
            email.text = adm.faculty_email
            sub.text = adm.faculty_sub
            if(adm.faculty_image!=null) {
                image.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        adm.faculty_image,
                        0,
                        adm.faculty_image!!.size
                    )
                )
            }
        }
    }
}
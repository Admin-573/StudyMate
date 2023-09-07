package com.example.studymate.admin

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.example.studymate.R
import com.example.studymate.database.SQLiteHelper

class Admin_view : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var adminSession: AdminSession
    private lateinit var id : EditText
    private lateinit var name: EditText
    private lateinit var email : EditText
    private lateinit var image : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_view)
        sqLiteHelper = SQLiteHelper(this)
        adminSession = AdminSession(this)

        id = findViewById(R.id.Admin_updateId)
        name = findViewById(R.id.Admin_updatename)
        email = findViewById(R.id.Admin_updateemail)
        image = findViewById(R.id.Admin_updateimage)

        val adminEmail = adminSession.sharedPreferences.getString("email","").toString()
        Log.d("sessionMail", adminEmail)

        if(adminEmail!=null){
            val admin = sqLiteHelper.getAdmin(adminEmail)
            if(admin.isNotEmpty()){
                id.setText(admin[0].admin_id.toString())
                name.setText(admin[0].admin_name)
                email.setText(admin[0].admin_email)
                if(admin[0].admin_image!=null) {
                    image.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                            admin[0].admin_image,
                            0,
                            admin[0].admin_image!!.size
                        )
                    )
                }else{
                    image.setImageDrawable(resources.getDrawable(R.drawable.add_img))
                }
            }
        }
    }
}
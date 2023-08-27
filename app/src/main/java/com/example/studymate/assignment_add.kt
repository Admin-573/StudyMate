package com.example.studymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.datepicker.MaterialDatePicker

class assignment_add : AppCompatActivity() {
    private lateinit var assignment_name : EditText
    private lateinit var assignment_sdate : EditText
    private lateinit var assignment_type : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment_add)
        initView()
    }

    private fun initView() {
        assignment_name = findViewById(R.id.Admin_add_assignment_name)
        assignment_sdate = findViewById(R.id.Admin_add_assignment_sdate)
        assignment_type = findViewById(R.id.Admin_add_assignment_type)
        assignment_sdate.setOnClickListener {
            val materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Assignment Submission Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .build()

            materialDatePicker.addOnPositiveButtonClickListener {
                assignment_sdate.setText(materialDatePicker.headerText)
            }

            materialDatePicker.show(supportFragmentManager,"Material Date Picker")
        }
    }
}
package com.example.studymate

import android.app.Application
import com.google.android.material.color.DynamicColors

class StudyMate : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
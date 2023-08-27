package com.example.studymate

data class AdminModel (
    // Admin Content
    var admin_name : String = "",
    var admin_email : String = "",

    // Faculty Content
    var faculty_name : String = "",
    var faculty_email : String = "",
    var faculty_password : String = "",
    var faculty_sub : String = "",

    // Student Content
    var student_name : String = "",
    var student_email : String = "",
    var student_password : String = "",
    var student_class : String = "",

    //Notice Content

)
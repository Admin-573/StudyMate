package com.example.studymate.database

data class AdminModel (
    // Admin Content
    var admin_image : ByteArray?= null,
    var admin_name : String = "",
    var admin_email : String = "",

    // Faculty Content
    var faculty_id : Int? = null,
    var faculty_image : ByteArray? = null,
    var faculty_name : String = "",
    var faculty_email : String = "",
    var faculty_password : String = "",
    var faculty_sub : String = "",

    // Student Content
    var student_id : Int? = null,
    var student_image : ByteArray? = null,
    var student_name : String = "",
    var student_email : String = "",
    var student_password : String = "",
    var student_class : String = "",

    //Notice Content
    var notice_name : String = "",
    var notice_des : String = "",
    var notice_date : String = "",

    //Assignment Content
    var assignment_name : String = "",
    var assignment_sdate : String = "",
    var assignment_type : String = "",
)
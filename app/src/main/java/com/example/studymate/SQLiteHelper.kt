package com.example.studymate

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1

        //Admin Table
        private const val TBL_ADMIN = "tbl_admin"
        private const val ADMIN_NAME = "admin_name"
        private const val ADMIN_EMAIL = "admin_email"

        //Faculty Table
        private const val TBL_FACULTY = "tbl_faculty"
        private const val FACULTY_NAME = "faculty_name"
        private const val FACULTY_EMAIL = "faculty_email"
        private const val FACULTY_PASSWORD = "faculty_password"
        private const val FACULTY_SUB = "faculty_sub"

        //Student Table
        private const val TBL_STUDENT = "tbl_student"
        private const val STUDENT_NAME = "student_name"
        private const val STUDENT_EMAIL = "student_email"
        private const val STUDENT_PASSWORD = "student_password"
        private const val STUDENT_CLASS = "student_class"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        //Admin Table
        val createTblAdmin = "CREATE TABLE $TBL_ADMIN($ADMIN_NAME TEXT,$ADMIN_EMAIL VARCHAR(128) PRIMARY KEY);"
        p0?.execSQL(createTblAdmin)

        //Faculty Table
        val createTblFaculty = "CREATE TABLE $TBL_FACULTY($FACULTY_NAME TEXT,$FACULTY_EMAIL VARCHAR(128) PRIMARY KEY,$FACULTY_PASSWORD TEXT,$FACULTY_SUB VARCHAR(256));"
        p0?.execSQL(createTblFaculty)

        //Student Table
        val createTblStudent = "CREATE TABLE $TBL_STUDENT($STUDENT_NAME TEXT,$STUDENT_EMAIL VARCHAR(128) PRIMARY KEY,$STUDENT_PASSWORD TEXT,$STUDENT_CLASS VARCHAR(256));"
        p0?.execSQL(createTblStudent)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //Admin Table
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_ADMIN")
        onCreate(p0)

        //Faculty Table
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_FACULTY")
        onCreate(p0)

        //Student Table
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(p0)
    }

    //Inserting Data Of Admin
    fun InsertAdmin(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ADMIN_NAME,adm.admin_name)
        contentValues.put(ADMIN_EMAIL,adm.admin_email)

        val insertQuery = db.insert(TBL_ADMIN,null,contentValues)
        db.close()
        return insertQuery
    }

    //Inserting Data Of Faculty
    fun InsertFaculty(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(FACULTY_NAME,adm.faculty_name)
        contentValues.put(FACULTY_EMAIL,adm.faculty_email)
        contentValues.put(FACULTY_PASSWORD,adm.faculty_password)
        contentValues.put(FACULTY_SUB,adm.faculty_sub)

        val insertQuery = db.insert(TBL_FACULTY,null,contentValues)
        db.close()
        return insertQuery
    }

    //Displaying Data Of Faculty
    @SuppressLint("Range")
    fun getAllFaculty() : ArrayList<AdminModel>
    {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_FACULTY"
        val db = this.writableDatabase

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var name : String
        var email : String
        var password : String
        var subject : String

        if(cursor.moveToFirst()){
            do{

                name = cursor.getString(cursor.getColumnIndex("faculty_name"))
                email = cursor.getString(cursor.getColumnIndex("faculty_email"))
                password = cursor.getColumnName(cursor.getColumnIndex("faculty_password"))
                subject = cursor.getString(cursor.getColumnIndex("faculty_sub"))

                val adm = AdminModel(faculty_name = name, faculty_email = email, faculty_password = password, faculty_sub = subject)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }

    //Deleting Data Of Faculty
    fun DeleteFaculty(email: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(FACULTY_EMAIL,email)

        val DeleteQuery = db.delete(TBL_FACULTY,"faculty_email=$FACULTY_EMAIL",null)
        db.close()
        return DeleteQuery
    }

    //Inserting Data Of Student
    fun InsertStudent(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME,adm.student_name)
        contentValues.put(STUDENT_EMAIL,adm.student_email)
        contentValues.put(STUDENT_PASSWORD,adm.student_password)
        contentValues.put(STUDENT_CLASS,adm.student_class)

        val insertQuery = db.insert(TBL_STUDENT,null,contentValues)
        db.close()
        return insertQuery
    }

}
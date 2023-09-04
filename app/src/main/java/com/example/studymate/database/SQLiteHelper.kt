package com.example.studymate.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {

    companion object{
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1

        //Admin Table
        private const val TBL_ADMIN = "tbl_admin"
        private const val ADMIN_IMAGE = "admin_image"
        private const val ADMIN_NAME = "admin_name"
        private const val ADMIN_EMAIL = "admin_email"

        //Faculty Table
        private const val TBL_FACULTY = "tbl_faculty"
        private const val FACULTY_IMAGE = "faculty_image"
        private const val FACULTY_NAME = "faculty_name"
        private const val FACULTY_EMAIL = "faculty_email"
        private const val FACULTY_PASSWORD = "faculty_password"
        private const val FACULTY_SUB = "faculty_sub"

        //Student Table
        private const val TBL_STUDENT = "tbl_student"
        private const val STUDENT_IMAGE = "student_image"
        private const val STUDENT_NAME = "student_name"
        private const val STUDENT_EMAIL = "student_email"
        private const val STUDENT_PASSWORD = "student_password"
        private const val STUDENT_CLASS = "student_class"

        //Notice Table
        private const val TBL_NOTICE = "tbl_notice"
        private const val NOTICE_NAME = "notice_name"
        private const val NOTICE_DES = "notice_des"
        private const val NOTICE_DATE = "notice_date"

        //Assignment Table
        private const val TBL_ASSIGNMENT = "tbl_assignment"
        private const val ASSIGNMENT_NAME = "assignment_name"
        private const val ASSIGNMENT_TYPE = "assignment_type"
        private const val ASSIGNMENT_SDATE = "assignment_sdate"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        //Admin Table
        val createTblAdmin = "CREATE TABLE $TBL_ADMIN($ADMIN_IMAGE BLOB,$ADMIN_NAME TEXT NOT NULL,$ADMIN_EMAIL VARCHAR(128) PRIMARY KEY);"
        p0?.execSQL(createTblAdmin)

        //Faculty Table
        val createTblFaculty = "CREATE TABLE $TBL_FACULTY($FACULTY_IMAGE BLOB,$FACULTY_NAME TEXT,$FACULTY_EMAIL VARCHAR(128) PRIMARY KEY,$FACULTY_PASSWORD TEXT,$FACULTY_SUB VARCHAR(256));"
        p0?.execSQL(createTblFaculty)

        //Student Table
        val createTblStudent = "CREATE TABLE $TBL_STUDENT($STUDENT_IMAGE BLOB,$STUDENT_NAME TEXT,$STUDENT_EMAIL VARCHAR(128) PRIMARY KEY,$STUDENT_PASSWORD TEXT,$STUDENT_CLASS VARCHAR(256));"
        p0?.execSQL(createTblStudent)

        //Notice Table
        val createTblNotice = "CREATE TABLE $TBL_NOTICE($NOTICE_NAME TEXT PRIMARY KEY,$NOTICE_DES VARCHAR(512),$NOTICE_DATE TEXT);"
        p0?.execSQL(createTblNotice)

        //Assignment Table
        val createTblAssignment = "CREATE TABLE $TBL_ASSIGNMENT($ASSIGNMENT_NAME TEXT PRIMARY KEY,$ASSIGNMENT_SDATE TEXT,$ASSIGNMENT_TYPE VARCHAR(256));"
        p0?.execSQL(createTblAssignment)
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

        //Notice Table
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_NOTICE")
        onCreate(p0)

        //Assignment Table
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_ASSIGNMENT")
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
        contentValues.put(FACULTY_IMAGE,adm.faculty_image)
        contentValues.put(FACULTY_NAME,adm.faculty_name)
        contentValues.put(FACULTY_EMAIL,adm.faculty_email)
        contentValues.put(FACULTY_PASSWORD,adm.faculty_password)
        contentValues.put(FACULTY_SUB,adm.faculty_sub)

        val insertQuery = db.insert(TBL_FACULTY,null,contentValues)
        db.close()
        return insertQuery
    }
    fun getAdmin(email : String) : ArrayList<AdminModel>{
        val db  = this.readableDatabase
        val adminImageList : ArrayList<AdminModel> = ArrayList()
        val cursor : Cursor?
        try{
            cursor = db.rawQuery("SELECT * FROM $TBL_ADMIN WHERE $ADMIN_EMAIL = '$email' ",null)
        }catch (e:SQLiteException){
            e.printStackTrace()
            return adminImageList
        }
        if(cursor.moveToFirst()){
            do{
                val admin = AdminModel(
                    admin_image = cursor.getBlob(cursor.getColumnIndex("admin_image")),
                    admin_email = cursor.getString(cursor.getColumnIndex("admin_email")),
                    admin_name = cursor.getString(cursor.getColumnIndex("admin_name"))
                )
                adminImageList.add(admin)
            }while (cursor.moveToNext())
        }
        return adminImageList
    }

    fun updateImage(adm: AdminModel): Int {

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ADMIN_IMAGE,adm.admin_image)
        val email = adm.admin_email
        val uploadImage = db.update(TBL_ADMIN,contentValues, "$ADMIN_EMAIL = '$email'",null)
        db.close()
        return  uploadImage
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

        var image:ByteArray
        var name : String
        var email : String
        var password : String
        var subject : String

        if(cursor.moveToFirst()){
            do{
                image = cursor.getBlob(cursor.getColumnIndex(FACULTY_IMAGE))
                name = cursor.getString(cursor.getColumnIndex("faculty_name"))
                email = cursor.getString(cursor.getColumnIndex("faculty_email"))
                password = cursor.getString(cursor.getColumnIndex("faculty_password"))
                subject = cursor.getString(cursor.getColumnIndex("faculty_sub"))

                val adm = AdminModel(faculty_image = image,faculty_name = name, faculty_email = email, faculty_password = password, faculty_sub = subject)
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

        val DeleteQuery = db.delete(TBL_FACULTY, "$FACULTY_EMAIL = '$email' " ,null)
        db.close()
        return DeleteQuery
    }

    //Updating Data Of Faculty
    fun updateFacultyByEmail(adm: AdminModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(FACULTY_NAME,adm.faculty_name)
        contentValues.put(FACULTY_SUB,adm.faculty_sub)
        contentValues.put(FACULTY_PASSWORD,adm.faculty_password)

        val email = adm.faculty_email
        val UpdateQuery = db.update(TBL_FACULTY,contentValues,"$FACULTY_EMAIL = '$email'",null)
        db.close()
        return UpdateQuery
    }

    //Inserting Data Of Student
    fun InsertStudent(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(STUDENT_IMAGE,adm.student_image)
        contentValues.put(STUDENT_NAME,adm.student_name)
        contentValues.put(STUDENT_EMAIL,adm.student_email)
        contentValues.put(STUDENT_PASSWORD,adm.student_password)
        contentValues.put(STUDENT_CLASS,adm.student_class)

        val insertQuery = db.insert(TBL_STUDENT,null,contentValues)
        db.close()
        return insertQuery
    }

    //Displaying Data Of Student
    @SuppressLint("Range")
    fun getAllStudent() : ArrayList<AdminModel>
    {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.writableDatabase

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var image : ByteArray
        var name : String
        var email : String
        var password : String
        var stud_class : String

        if(cursor.moveToFirst()){
            do{
                image = cursor.getBlob(cursor.getColumnIndex(STUDENT_IMAGE))
                name = cursor.getString(cursor.getColumnIndex("student_name"))
                email = cursor.getString(cursor.getColumnIndex("student_email"))
                password = cursor.getString(cursor.getColumnIndex("student_password"))
                stud_class = cursor.getString(cursor.getColumnIndex("student_class"))

                val adm = AdminModel(student_name = name, student_email = email, student_password = password, student_class = stud_class, student_image = image)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }

    //Deleting Data Of Student
    fun DeleteStudent(email: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(STUDENT_EMAIL,email)

        val DeleteQuery = db.delete(TBL_STUDENT,"$STUDENT_EMAIL = '$email' ",null)
        db.close()
        return DeleteQuery
    }

    //Updating Data Of Student
    fun updateStudentByEmail(adm: AdminModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME,adm.student_name)
        contentValues.put(STUDENT_PASSWORD,adm.student_password)
        contentValues.put(STUDENT_CLASS,adm.student_class)

        val email = adm.student_email
        val UpdateQuery = db.update(TBL_STUDENT,contentValues,"$STUDENT_EMAIL = '$email'",null)
        db.close()
        return UpdateQuery
    }


    //Inserting Notices
    fun InsertNotice(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NOTICE_NAME,adm.notice_name)
        contentValues.put(NOTICE_DES,adm.notice_des)
        contentValues.put(NOTICE_DATE,adm.notice_date)

        val insertQuery = db.insert(TBL_NOTICE,null,contentValues)
        db.close()
        return insertQuery
    }

    //Displaying Notices
    @SuppressLint("Range")
    fun getAllNotice(): ArrayList<AdminModel> {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_NOTICE"
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
        var des : String
        var ndate : String

        if(cursor.moveToFirst()){
            do{

                name = cursor.getString(cursor.getColumnIndex("notice_name"))
                des = cursor.getString(cursor.getColumnIndex("notice_des"))
                ndate = cursor.getString(cursor.getColumnIndex("notice_date"))

                val adm = AdminModel(notice_name = name, notice_des = des, notice_date = ndate)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }

    //Delete Notices
    fun DeleteNotice(name_notice: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NOTICE_NAME,name_notice)

        val DeleteQuery = db.delete(TBL_NOTICE,"notice_name=$NOTICE_NAME",null)
        db.close()
        return DeleteQuery
    }


    //Inserting Assignments
    fun InsertAssignment(adm: AdminModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ASSIGNMENT_NAME,adm.assignment_name)
        contentValues.put(ASSIGNMENT_SDATE,adm.assignment_sdate)
        contentValues.put(ASSIGNMENT_TYPE,adm.assignment_type)

        val insertQuery = db.insert(TBL_ASSIGNMENT,null,contentValues)
        db.close()
        return insertQuery
    }

    //Displaying Assignments
    @SuppressLint("Range")
    fun getAllAssignment(): ArrayList<AdminModel> {
        val admList : ArrayList<AdminModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ASSIGNMENT"
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
        var sdate : String
        var stype : String

        if(cursor.moveToFirst()){
            do{

                name = cursor.getString(cursor.getColumnIndex("assignment_name"))
                sdate = cursor.getString(cursor.getColumnIndex("assignment_sdate"))
                stype = cursor.getString(cursor.getColumnIndex("assignment_type"))

                val adm = AdminModel(assignment_name = name, assignment_sdate = sdate, assignment_type = stype)
                admList.add(adm)

            } while (cursor.moveToNext())
        }
        return admList
    }

    //Deleting Assignments
    fun DeleteAssignment(name_assign: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ASSIGNMENT_NAME,name_assign)

        val DeleteQuery = db.delete(TBL_ASSIGNMENT,"assignment_name=$ASSIGNMENT_NAME",null)
        db.close()
        return DeleteQuery
    }

}
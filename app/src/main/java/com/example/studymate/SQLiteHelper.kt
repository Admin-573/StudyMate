package com.example.studymate

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_ADMIN = "tbl_admin"
        private const val ADMIN_NAME = "admin_name"
        private const val ADMIN_EMAIL = "admin_email"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTblAdmin = "CREATE TABLE $TBL_ADMIN($ADMIN_NAME TEXT,$ADMIN_EMAIL TEXT UNIQUE);"
        p0?.execSQL(createTblAdmin)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_ADMIN")
        onCreate(p0)
    }

    fun InsertAdmin(adm : AdminModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ADMIN_NAME,adm.admin_name)
        contentValues.put(ADMIN_EMAIL,adm.admin_email)

        val insertQuery = db.insert(TBL_ADMIN,null,contentValues)
        db.close()
        return insertQuery
    }

}
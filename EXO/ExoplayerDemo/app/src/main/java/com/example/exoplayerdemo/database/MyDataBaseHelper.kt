package com.example.exoplayerdemo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 *  启动SQLite数据库
 *  context:对应activity的上下文
 *  name:需要操作的数据库的名字
 *  version:需要操作的数据库的版本
 */
class MyDataBaseHelper(val context: Context, val name: String, val versin: Int) :
    SQLiteOpenHelper(context, name, null, versin) {
    private val createUriData = "create table $name (" +
            "id integer primary key autoincrement, " +
            "streamName text," +
            "streamPath text," +
            "streamDescription text)"


    /*
     * 创建数据库
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createUriData)
        Toast.makeText(context, "Create $name succeeded", Toast.LENGTH_LONG).show()
    }

    /*
     * 数据库升级
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Toast.makeText(context, "upgrade $name succeeded", Toast.LENGTH_LONG).show()
        db.execSQL("drop table if exists $name")
        onCreate(db)
    }
}
package com.example.exoplayerdemo.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import com.example.exoplayerdemo.common.MediaStream

/**
 *  数据库封装类，用于其他activity调用以操作数据库
 */
class MediaStreamDatabaseControl(val context: Context, val name: String, val versin: Int) {

    private var dbHelper : MyDataBaseHelper = MyDataBaseHelper(context, name, versin)

    /*
     * 创建数据库
     */
    fun create(){
        dbHelper.writableDatabase
    }

    /*
     * 添加数据
     */
    fun addData(mediaStream: MediaStream){
        val db = dbHelper.writableDatabase

        /*
         * 这里写入数据库的操作没有进行编码，中文读出来就可能是乱码
         */
        val value = ContentValues().apply {
            put("streamName", mediaStream.streamName)
            put("streamPath", mediaStream.streamPath)
            put("streamDescription", mediaStream.streamDescription)
        }
        db.insert(name, null, value)

    }

    /*
     * 查询数据：通过码流名字索引码流
     */
    fun queryData(targetName:  String) : MediaStream? {
        val db = dbHelper.writableDatabase
        /*
         * 数据库索引规则：全库搜索
         */
        val cursor = db.query(name, null,
            null, null, null,
            null, null, null)
        /*
         * 数据库遍历
         */
        if (cursor.moveToFirst()) {
            do {
                val streamName = cursor.getString(cursor.getColumnIndex("streamName"))
                val streamPath = cursor.getString(cursor.getColumnIndex("streamPath"))
                val streamDescription = cursor.getString(cursor.getColumnIndex("streamDescription"))

                /* 搜索到对应MediaStream */
                if (targetName == streamName) {
                    cursor.close()
                    return MediaStream(streamName, streamPath, streamDescription)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        /*
         * 如果未遍历到目标数据，则返回null
         */
        return null
    }

    /*
     * 遍历全局数据库
     */
    fun queryAllData(name: String) : ArrayList<MediaStream>{
        val db = dbHelper.writableDatabase
        val dataList = ArrayList<MediaStream>()

        /* 全局搜索 */
        val cursor = db.query(name, null,
            null, null, null,
            null, null, null)

        /* 遍历数据库 */
        if (cursor.moveToFirst()){
            do {
                val streamName = cursor.getString(cursor.getColumnIndex("streamName"))
                val streamPath = cursor.getString(cursor.getColumnIndex("streamPath"))
                val streamDescription = cursor.getString(cursor.getColumnIndex("streamDescription"))
                dataList.add(MediaStream(streamName, streamPath, streamDescription))
            } while (cursor.moveToNext())
            cursor.close()
            return dataList
        }
        cursor.close()
        /* 如果未遍历到目标数据，则返回null */
        return dataList
    }

    /*
     * 更新数据：通过数据库的id索引对应的媒体流信息
     * position：目标id
     */
    fun updateData(mediaStream: MediaStream, position: Int) {
        val db = dbHelper.writableDatabase
        val value = ContentValues().apply {
            put("streamName", mediaStream.streamName)
            put("streamPath", mediaStream.streamPath)
            put("streamDescription", mediaStream.streamDescription)
        }
        db.update(name, value, "id = ?", arrayOf(position.toString()))
    }

    /*
     * 更新数据：检索依据为媒体流名字（暂定）
     */
    fun updateData(mediaStream: MediaStream) {
        val db = dbHelper.writableDatabase

        val value = ContentValues().apply {
            put("streamName", mediaStream.streamName)
            put("streamPath", mediaStream.streamPath)
            put("streamDescription", mediaStream.streamDescription)
        }
        /* 全局搜索 */
        val cursor = db.query(name, null,
            null, null, null,
            null, null, null)
        /* 遍历数据库 */
        if (cursor.moveToFirst()){
            do {
                val streamName = cursor.getString(cursor.getColumnIndex("streamName"))
                if (streamName == mediaStream.streamName){
                    db.update(name, value, "streamName = ?", arrayOf(streamName))
                }
            } while (cursor.moveToNext())
            cursor.close()
        }
    }

    /* 获取数据库总数据条数 */
    @SuppressLint("Recycle")
    fun getDataLengh() : Int{
        val db = dbHelper.writableDatabase
        /* 全局搜索 */
        val cursor = db.query(name, null,
            null, null, null,
            null, null, null)

        return cursor.count
    }

    /* 删除数据:根据媒体流名字来删除 */
    fun deleteData(streamName: String){
        val db = dbHelper.writableDatabase
        db.delete(name, "streamName = ?", arrayOf(streamName))
    }
}
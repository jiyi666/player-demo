package com.example.exoplayerdemo.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exoplayerdemo.R
import com.example.exoplayerdemo.activity.utils.BaseActivity
import com.example.exoplayerdemo.adapter.MediaStreamDataAdapter
import com.example.exoplayerdemo.common.MediaStream
import com.example.exoplayerdemo.database.MediaStreamDatabaseControl
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : BaseActivity() {

    private val tag = "MainActivity"
    private var mediaStreamList = ArrayList<MediaStream>()

    /* 消息集 */
    val updateDataFromInternet = 1
    /*
     * 使用数据库
     */
    val mediaStreamDatabase = MediaStreamDatabaseControl(this, "MediaStream", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * 将当前活动赋值给静态对象
         */
        mainActivity = this

        /*
         * 如果数据库不存在：创建 + 添加初始数据
         */
        if (mediaStreamDatabase.queryAllData("MediaStream").size == 0){
            /* 创建数据库 */
            mediaStreamDatabase.create()
            /*
             * 初始化stock数据
             */
            initMediaStream()
            /* 添加数据 */
            for (i in 0 until (mediaStreamList.size)){
                mediaStreamDatabase.addData(mediaStreamList.get(index = i))
            }
        }

        /* 添加媒体资源的listener */
        addNewItem.setOnClickListener {
            //TODO
        }
    }

    override fun onResume() {
        super.onResume()
        /*
         * 每次返回该Activity都重新读取并刷新UI
         */
        mediaStreamList.clear()
        mediaStreamList = mediaStreamDatabase.queryAllData("MediaStream")

        /*
         * 获取layoutManager
         */
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        /*
         * 获取适配器
         */
        val adapter = MediaStreamDataAdapter(mediaStreamList)
        recyclerView.adapter = adapter
    }

    private fun initMediaStream(){
        mediaStreamList.add(MediaStream("gangganghao.mp4", getMediaFileUri(this, "gangganghao.mp4").toString(), "本地文件：刚刚好.mp4"))
        mediaStreamList.add(MediaStream("sudi.mp4", getMediaFileUri(this, "sudi.mp4").toString(), "本地文件：天龙八部之宿敌.mp4"))
    }

    fun getMediaFileUri(context: Context, fileName: String?): Uri? {
        /* 从/storage/emulated/0/Android/data/com.example.exoplayertest/files/下面获取文件 */
        val filePath = context.getExternalFilesDir(null)!!.path
        val file = File(filePath, fileName)
        var uri: Uri? = null
        if (file.exists()) {
            uri = Uri.fromFile(file)
        }
        return uri
    }
    /**
     *  MainActivity的单实例，用于供外部类调用的static方法等
     */
    companion object{

        const val STARTDETAILACTIVITY = "startdetailactivity"
        const val HANDLELONGCLIECK = "handlelongclick"
        lateinit var  mainActivity : BaseActivity   //静态对象，用于适配器调用activity的相关操作

        /**
         * mainActivityTodo由外部类回调MainActivity操作
         * event：需要执行的操作
         * streamName：响应控件对应的码流名称
         */
        @JvmStatic
        fun mainActivityTodo(event: String, streamName: String){
            when (event){
                /* 启动DetailActivity */
                STARTDETAILACTIVITY -> {
                    Log.d("jiyi", "streamName:$streamName")
                    //TODO:
                }
                /* 处理长按删除item事件 */
                HANDLELONGCLIECK -> {
                    Log.d("jiyi", "streamName:$streamName")
                    //TODO:
                }
            }
        }
    }
}
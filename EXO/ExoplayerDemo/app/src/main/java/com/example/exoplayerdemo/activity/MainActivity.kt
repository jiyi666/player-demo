package com.example.exoplayerdemo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exoplayerdemo.R
import com.example.exoplayerdemo.activity.utils.BaseActivity
import com.example.exoplayerdemo.adapter.MediaStreamDataAdapter
import com.example.exoplayerdemo.common.MediaStream
import com.example.exoplayerdemo.database.MediaStreamDatabaseControl
import kotlinx.android.synthetic.main.activity_main.*

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
             * 初始化MediaStream数据
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
        mediaStreamList.add(MediaStream("gangganghao.mp4", "/storage/emulated/0/Android/data/com.example.exoplayerdemo/files/gangganghao.mp4", "本地文件：刚刚好.mp4"))
        mediaStreamList.add(MediaStream("sudi.mp4", "/storage/emulated/0/Android/data/com.example.exoplayerdemo/files/sudi.mp4", "本地文件：天龙八部之宿敌.mp4"))
        mediaStreamList.add(MediaStream("DASH:gangtiezhilei", "http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd", "DASH流：钢铁之类测试片段"))
        mediaStreamList.add(MediaStream("HLS:test stream 0", "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8", "HLS流：动画片"))
        mediaStreamList.add(MediaStream("HLS:test stream 01", "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8", "HLS流：忘了是啥了"))
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
         * targetStr：响应控件对应的码流名称/码流路径
         */
        @JvmStatic
        fun mainActivityTodo(event: String, targetStr: String?){
            when (event){
                /* 启动DetailActivity */
                STARTDETAILACTIVITY -> {
                    //TODO:
                    Log.d("MainActivity", "streamPath:$targetStr")
                    val intent = Intent(mainActivity, PlayerActivity::class.java).apply {
                        putExtra("streamPath", targetStr)
                    }
                    mainActivity.startActivity(intent)
                }
                /* 处理长按删除item事件 */
                HANDLELONGCLIECK -> {
                    Log.d("ExoTest", "streamName:$targetStr")
                    //TODO:
                }
            }
        }
    }
}
package com.example.exoplayerdemo.activity

import android.os.Bundle
import android.util.Log
import com.example.exoplayerdemo.R
import com.example.exoplayerdemo.activity.utils.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                    //TODO:
                }
                /* 处理长按删除item事件 */
                HANDLELONGCLIECK -> {
                    //TODO:
                }
            }
        }
    }
}
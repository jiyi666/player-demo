package com.example.exoplayerdemo.activity.utils

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.financialfreedom.utils.ActivityCollector

open class BaseActivity : AppCompatActivity(){

    /*
     * 常量：类tag
     */
    private val tag = "BaseActivity"

    /*
     * 覆写onCreate方法
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        Log.d(tag, "onCreate " + javaClass.simpleName)    //打印当前类名
    }

    /*
     * 覆写onDestroy方法
     */
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}
package com.example.financialfreedom.utils

import android.app.Activity
import java.util.ArrayList

/** 创建单例类ActivityCollector用于管理所有的activity */
object ActivityCollector {

    /*
     * 常量：activity集合
     */
    private val activities = ArrayList<Activity>()

    /*
     * 添加activity进集合
     */
    fun addActivity(activity: Activity){
        activities.add(activity)
    }

    /*
     * 移除activity出集合
     */
    fun removeActivity(activity: Activity){
        activities.remove(activity)
    }

    /*
     * 退出所有的activity
     */
    fun finishAll(){
        for (activity in activities){
            if (!activity.isFinishing){
                activity.finish()
            }
        }
        activities.clear()  //清空activity集合
    }
}
package com.example.exoplayerdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.exoplayerdemo.R
import com.example.exoplayerdemo.activity.MainActivity
import com.example.exoplayerdemo.common.MediaStream

/** RecyclerView的适配器 */
class MediaStreamDataAdapter(list : ArrayList<MediaStream>) :
    RecyclerView.Adapter <MediaStreamDataAdapter.ViewHolder>() {

    private var mediaStreamDataList = ArrayList<MediaStream>()

    init {
        mediaStreamDataList = list
    }

    /*
     * 用于获取最外层布局的及控件的实例
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val streamDescription : TextView = view.findViewById(R.id.streamDescription)
    }

    /*
     * 加载uri_data布局
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context)
            .inflate(R.layout.mediastream_data, parent, false)

        val viewHolder = ViewHolder(view)
        /*
         * 最外层布局空白处点击事件
         */
        viewHolder.itemView.setOnClickListener{
            Toast.makeText(parent.context, "you click what?",
                Toast.LENGTH_SHORT).show()
        }

        /**
         *  长按监听：删除item
         */
        viewHolder.itemView.setOnLongClickListener {
            val position = viewHolder.adapterPosition
            /* 将长按item对应的[码流名字]发送至MainActivity */
            MainActivity.mainActivityTodo(
                MainActivity.HANDLELONGCLIECK,
                mediaStreamDataList[position].streamName)
            /* 在ArrayList中移除此股 */
            mediaStreamDataList.remove(mediaStreamDataList[position])
            /* 通知移除该item */
            notifyItemRemoved(position)
            /* 通知调制ArrayList顺序(此句删除也无影响) */
            notifyItemRangeChanged(position, mediaStreamDataList.size)
            false
        }

        return viewHolder //注意这里要返回viewHolder，因为有各种点击事件
    }

    /**
     * 调用adapter的notifyItemChanged时会调用此函数，用于更新局部控件
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()){
            onBindViewHolder(holder, position)
        } else {
            //TODO?
        }
    }
    /*
     * 对RecyclerView滚入屏幕的子项数据赋值
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mediaStream = mediaStreamDataList[position]
        holder.streamDescription.text = mediaStream.streamDescription
    }

    /*
     * 返回数据源长度
     */
    override fun getItemCount() = mediaStreamDataList.size
}
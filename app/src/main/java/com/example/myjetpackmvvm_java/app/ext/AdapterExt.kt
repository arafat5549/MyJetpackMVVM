package com.example.myjetpackmvvm_java.app.ext

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.myjetpackmvvm_java.Const

/**
 * 作者　: WangYao
 * 时间　: 2020/4/16
 * 描述　: 设置Adapter点击
 */
var adapterlastClickTime = 0L
fun BaseQuickAdapter<*, *>.setNbOnItemClickListener(interval: Long = Const.interval_item, action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit) {
    setOnItemClickListener { adapter, view, position ->
         val currentTime = System.currentTimeMillis()
         if (adapterlastClickTime != 0L && (currentTime - adapterlastClickTime < interval)) {
             return@setOnItemClickListener
         }
        adapterlastClickTime = currentTime
        action(adapter,view,position)
    }
}

var adapterchildlastClickTime = 0L
fun BaseQuickAdapter<*, *>.setNbOnItemChildClickListener(interval: Long = Const.interval_item,action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit) {
    setOnItemChildClickListener { adapter, view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterchildlastClickTime != 0L && (currentTime - adapterchildlastClickTime < interval)) {
            return@setOnItemChildClickListener
        }
        adapterchildlastClickTime = currentTime
        action(adapter,view,position)
    }
}
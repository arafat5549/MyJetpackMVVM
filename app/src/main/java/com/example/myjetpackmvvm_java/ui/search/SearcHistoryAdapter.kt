package com.example.myjetpackmvvm_java.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.ext.setAdapterAnimion
import com.example.myjetpackmvvm_java.app.util.SettingUtil

class SearcHistoryAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_history, data) {

    init {
        setAdapterAnimion(SettingUtil.getListMode())
    }

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.item_history_text, item)
    }

}
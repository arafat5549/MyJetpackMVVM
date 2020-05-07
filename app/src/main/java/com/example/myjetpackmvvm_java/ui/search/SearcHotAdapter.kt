package com.example.myjetpackmvvm_java.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.ext.setAdapterAnimion
import com.example.myjetpackmvvm_java.app.util.ColorUtil
import com.example.myjetpackmvvm_java.app.util.SettingUtil
import com.example.myjetpackmvvm_java.data.entity.SearchResponse

class SearcHotAdapter(data: ArrayList<SearchResponse>) :
    BaseQuickAdapter<SearchResponse, BaseViewHolder>(R.layout.flow_layout, data) {

    init {
        setAdapterAnimion(SettingUtil.getListMode())
    }

    override fun convert(helper: BaseViewHolder, item: SearchResponse) {
        helper.setText(R.id.flow_tag, item.name)
        helper.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}
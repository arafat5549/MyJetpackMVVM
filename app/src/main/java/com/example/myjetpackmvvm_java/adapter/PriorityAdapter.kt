package com.example.myjetpackmvvm_java.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.weight.preference.MyColorCircleView
import com.example.myjetpackmvvm_java.data.enums.TodoType

/**
 * 重要程度 adapter
 * @Author:         WangYao
 * @CreateDate:     2019/9/1 9:52
 */
class PriorityAdapter(data: ArrayList<TodoType>) : BaseQuickAdapter<TodoType, BaseViewHolder>(R.layout.item_todo_dialog, data) {
    var checkType = TodoType.TodoType1.type

    constructor(data: ArrayList<TodoType>, checkType: Int) : this(data) {
        this.checkType = checkType
    }

    override fun convert(helper: BaseViewHolder, item: TodoType) {
        //赋值
        item.run {
            helper.setText(R.id.item_todo_dialog_name, item.content)
            if (checkType == item.type) {
                helper.getView<MyColorCircleView>(R.id.item_todo_dialog_icon).setViewSelect(item.color)
            } else {
                helper.getView<MyColorCircleView>(R.id.item_todo_dialog_icon).setView(item.color)
            }
        }
    }
}



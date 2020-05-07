package com.example.myjetpackmvvm_java.app.weight.banner

/**
 * 作者　: WangYao
 * 时间　: 2020/2/20
 * 描述　:
 */

import android.view.View
import android.widget.TextView
import com.example.myjetpackmvvm_java.R
import com.zhpan.bannerview.holder.ViewHolder

class WelcomeBannerViewHolder : ViewHolder<String> {
    override fun getLayoutId(): Int {
        return R.layout.banner_itemwelcome
    }

    override fun onBind(itemView: View, data: String?, position: Int, size: Int) {
        val textView = itemView.findViewById<TextView>(R.id.banner_text)
        textView.text = data
    }

}

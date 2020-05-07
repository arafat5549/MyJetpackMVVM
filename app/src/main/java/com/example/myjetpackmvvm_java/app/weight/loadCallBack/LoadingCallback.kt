package com.example.myjetpackmvvm_java.app.weight.loadCallBack

import android.content.Context
import android.view.View
import com.example.myjetpackmvvm_java.R
import com.kingja.loadsir.callback.Callback


class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun getSuccessVisible(): Boolean {
        return super.getSuccessVisible()
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}

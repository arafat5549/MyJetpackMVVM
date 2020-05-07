package com.example.myjetpackmvvm_java.app.weight.loadCallBack


import com.example.myjetpackmvvm_java.R
import com.kingja.loadsir.callback.Callback



class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}

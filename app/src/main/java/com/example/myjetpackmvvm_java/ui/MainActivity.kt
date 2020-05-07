package com.example.myjetpackmvvm_java.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.base.BaseActivity
import com.example.myjetpackmvvm_java.app.util.SettingUtil
import com.example.myjetpackmvvm_java.app.util.StatusBarUtil
import com.example.myjetpackmvvm_java.databinding.ActivityMainBinding
import com.tencent.bugly.beta.Beta

class MainActivity : BaseActivity<MessageViewmodel, ActivityMainBinding>() {

    override fun layoutId() = R.layout.activity_main

    override fun initView() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        StatusBarUtil.setColor(this, SettingUtil.getColor(this), 0)

        //进入首页检查更新
        Beta.checkUpgrade(false, true)
    }


    override fun createObserver() {
        appViewModel.appColor.observe(this, Observer {
            it?.let {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
                StatusBarUtil.setColor(this, it, 0)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.main_navation).navigateUp()
    }

    override fun showToast(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

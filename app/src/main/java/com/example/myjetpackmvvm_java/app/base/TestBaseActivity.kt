package com.example.myjetpackmvvm_java.app.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import me.hgj.jetpackmvvm.BaseViewModel
import me.hgj.jetpackmvvm.BaseVmDbActivity

/**
 * 作者　: WangYao
 * 时间　: 2020/4/10
 * 描述　: 测试BaseActivity
 */
abstract class TestBaseActivity<Vm:BaseViewModel,Db:ViewDataBinding> : BaseVmDbActivity<Vm,Db>() {

   abstract override fun layoutId(): Int

    abstract override fun initView()

    abstract override fun createObserver()

    override fun showLoading(message: String) {}

    override fun dismissLoading() { }



}
package com.example.myjetpackmvvm_java.ui.test

import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.base.BaseFragment
import com.example.myjetpackmvvm_java.app.ext.init
import com.example.myjetpackmvvm_java.databinding.FragmentHomeBinding
import com.example.myjetpackmvvm_java.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

class TestFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        //状态页配置

    }

    override fun lazyLoadData() {

    }

    override fun createObserver() {

    }
}
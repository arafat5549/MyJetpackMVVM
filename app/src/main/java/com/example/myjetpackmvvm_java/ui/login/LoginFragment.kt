package com.example.myjetpackmvvm_java.ui.login

import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.include_toolbar.*
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.base.BaseFragment
import com.example.myjetpackmvvm_java.app.ext.hideSoftKeyboard
import com.example.myjetpackmvvm_java.app.ext.initClose
import com.example.myjetpackmvvm_java.app.ext.showMessage
import com.example.myjetpackmvvm_java.app.util.CacheUtil
import com.example.myjetpackmvvm_java.app.util.SettingUtil
import com.example.myjetpackmvvm_java.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.util.setOnclickNoRepeat

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　:
 */
class LoginFragment : BaseFragment<LoginRegisterViewModel, FragmentLoginBinding>() {

    override fun layoutId() = R.layout.fragment_login

    override fun initView() {
        mDatabind.vm = mViewModel
        toolbar.initClose("登录") {
            Navigation.findNavController(it).navigateUp()
        }

        //设置颜色跟主题颜色一致
        appViewModel.appColor.value?.let {
            SettingUtil.setShapColor(loginSub, it)
            loginGoregister.setTextColor(it)
            toolbar.setBackgroundColor(it)
        }
    }

    override fun createObserver() {
        //监听请求结果
        mViewModel.loginResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                //登录成功 通知账户数据发生改变鸟
                CacheUtil.setUser(it)
                appViewModel.userinfo.postValue(it)
                Navigation.findNavController(toolbar).navigateUp()
            }, {
                //登录失败
                showMessage(it.errorMsg)
            })
        })
    }

    override fun lazyLoadData() {}
    /**
     * 设置点击事件 ,根据黄油刀的风格来仿写
     */
    override fun onViewClicked() {
        setOnclickNoRepeat(loginClear, loginSub, loginGoregister) {
            when (it.id) {
                R.id.loginClear -> {
                    mViewModel.username.set("")
                }
                R.id.loginSub -> {
                    when {
                        mViewModel.username.get().isEmpty() -> showMessage("请填写账号")
                        mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                        else -> mViewModel.loginReq()
                    }
                }
                R.id.loginGoregister -> {
                    hideSoftKeyboard(activity)
                    Navigation.findNavController(it)
                        .navigate(R.id.action_loginFragment_to_registerFrgment)
                }
            }
        }
    }
}
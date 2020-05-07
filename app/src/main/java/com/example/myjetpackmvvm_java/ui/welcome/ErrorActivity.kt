package com.example.myjetpackmvvm_java.ui.welcome

import android.content.ClipData
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.ToastUtils
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.base.BaseActivity
import com.example.myjetpackmvvm_java.app.ext.init
import com.example.myjetpackmvvm_java.app.util.SettingUtil
import com.example.myjetpackmvvm_java.app.util.StatusBarUtil
import com.example.myjetpackmvvm_java.databinding.ActivityErrorBinding
import kotlinx.android.synthetic.main.activity_error.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.BaseViewModel
import me.hgj.jetpackmvvm.ext.util.clipboardManager
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat


/**
 * 作者　: WangYao
 * 时间　: 2020/3/12
 * 描述　:
 */
class ErrorActivity : BaseActivity<BaseViewModel, ActivityErrorBinding>() {

    override fun layoutId() = R.layout.activity_error

    override fun initView()  {
        toolbar.init("发生错误")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        StatusBarUtil.setColor(this, SettingUtil.getColor(this), 0)
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        error_restart.clickNoRepeat{
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        error_sendError.setOnClickListener {
            // 创建普通字符型ClipData0
            val mClipData = ClipData.newPlainText("errorLog",CustomActivityOnCrash.getStackTraceFromIntent(intent))
            // 将ClipData内容放到系统剪贴板里。
            clipboardManager?.setPrimaryClip(mClipData)
            ToastUtils.showShort("已复制错误日志")
            try {
                val url = "mqqwpa://im/chat?chat_type=wpa&uin=824868922"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (e: Exception) {
                ToastUtils.showShort("请先安装QQ")
            }
        }
    }

    override fun createObserver() {

    }

    override fun showToast(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
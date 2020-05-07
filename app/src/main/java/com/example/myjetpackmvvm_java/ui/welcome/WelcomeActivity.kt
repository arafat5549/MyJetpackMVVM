package com.example.myjetpackmvvm_java.ui.welcome

import android.content.Intent
import com.blankj.utilcode.util.ConvertUtils
import com.example.myjetpackmvvm_java.R
import com.example.myjetpackmvvm_java.app.base.BaseActivity
import com.example.myjetpackmvvm_java.app.ext.setPageListener
import com.example.myjetpackmvvm_java.app.util.CacheUtil
import com.example.myjetpackmvvm_java.app.util.SettingUtil
import com.example.myjetpackmvvm_java.app.weight.banner.WelcomeBannerViewHolder
import com.example.myjetpackmvvm_java.databinding.ActivityWelcomeBinding
import com.example.myjetpackmvvm_java.ui.MainActivity
import com.example.myjetpackmvvm_java.ui.test.TestActivity
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.activity_welcome.*
import me.hgj.jetpackmvvm.BaseViewModel
import me.hgj.jetpackmvvm.ext.view.gone
import me.hgj.jetpackmvvm.ext.view.visible

/**
 * 作者　: WangYao
 * 时间　: 2020/2/22
 * 描述　:
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class WelcomeActivity : BaseActivity<BaseViewModel, ActivityWelcomeBinding>() {

    private var resList = arrayOf("唱", "跳", "r a p")

    private lateinit var mViewPager: BannerViewPager<String, WelcomeBannerViewHolder>

    override fun layoutId() = R.layout.activity_welcome

    override fun initView()  {
        //防止出现按Home键回到桌面时，再次点击重新进入该界面bug
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }
        welcome_baseview.setBackgroundColor(SettingUtil.getColor(this))
        mViewPager = findViewById(R.id.banner_view)
        if (CacheUtil.isFirst()) {
            //是第一次打开App 显示引导页
            welcome_image.gone()
            mViewPager.setHolderCreator {
                WelcomeBannerViewHolder()
            }.setIndicatorMargin(0, 0, 0, ConvertUtils.dp2px(24f))
                .setPageListener {
                    if (it == resList.size - 1) {
                        button2.visible()
                    } else {
                        button2.gone()
                    }
                }
            mViewPager.create(resList.toList())
        } else {
            //不是第一次打开App 0.3秒后自动跳转到主页
            welcome_image.visible()
            mViewPager.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                //带点渐变动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 300)
        }
        button2.setOnClickListener {
            CacheUtil.setFirst(false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            //带点渐变动画
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun createObserver() {

    }

    override fun showToast(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
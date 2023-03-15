package com.sun.mywidget.ui.main

import android.content.Intent
import androidx.activity.viewModels
import com.sun.base.base.BaseActivity
import com.sun.base.base.BaseProperties
import com.sun.base.expand.setOnSingleClickListener
import com.sun.mywidget.R
import com.sun.mywidget.databinding.ActivityMainBinding
import com.sun.mywidget.ui.dot.DotIndicatorActivity
import com.sun.mywidget.ui.pie.Pie3DViewActivity

class MainActivity : BaseActivity() {

    override val mProperties: BaseProperties
        get() = BaseProperties(
            layoutRes = R.layout.activity_main,
            hasTitle = true, titleStr = "主页",
            hasBack = true
        )

    private val mViewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.bind(mViewParent)
    }

    private val mViewModel by viewModels<MainViewModel>()

    override fun onLeftBackClick() {
        super.onLeftBackClick()
        closePage()
    }

    private fun closePage() {
        finish()
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        mViewBinding.pieBtn.setOnSingleClickListener {
            startActivity(Intent(this, Pie3DViewActivity::class.java))
        }
        mViewBinding.dotBtn.setOnSingleClickListener {
            startActivity(Intent(this, DotIndicatorActivity::class.java))
        }
    }
}
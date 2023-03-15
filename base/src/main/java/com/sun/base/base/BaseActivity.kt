package com.sun.base.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.jaeger.library.StatusBarUtil
import com.sun.base.databinding.ActivityBaseBinding
import com.sun.base.expand.adaptStatusBarHeight
import com.sun.base.expand.hide
import com.sun.base.expand.setOnSingleClickListener
import com.sun.base.expand.show

abstract class BaseActivity : AppCompatActivity() {

    abstract val mProperties: BaseProperties

    private val mViewBinding: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }

    protected val mViewParent: View by lazy {
        mViewBinding.viewStub.inflate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        mViewBinding.viewStub.layoutResource = mProperties.layoutRes
        setContentView(mViewBinding.root)

        val displayMetrics = resources.displayMetrics
        displayMetrics.scaledDensity = displayMetrics.density
        val controller = ViewCompat.getWindowInsetsController(mViewBinding.root)
        controller?.isAppearanceLightStatusBars = true
        controller?.isAppearanceLightNavigationBars = true

        StatusBarUtil.setTranslucentForImageView(this, 50, null)

        initTitleView()

        init()
    }

    private fun initTitleView() {
        with(mViewBinding) {
            statusBar.adaptStatusBarHeight()
            if (mProperties.hasTitle == true) {
                titleCl.show()
                mProperties.title?.let {
                    centerTv.text = getText(it)
                    centerTv.show()
                }
                mProperties.titleStr?.let {
                    centerTv.text = it
                    centerTv.show()
                }
                if (mProperties.hasBack == true) {
                    mProperties.backDrawableRes.let {
                        leftIv.setImageResource(it)
                    }
                    leftIv.show()
                    leftIv.setOnSingleClickListener {
                        onLeftBackClick()
                    }
                }
            } else {
                titleCl.hide()
            }
        }
    }

    fun showTitle(boolean: Boolean) {
        mViewBinding.headerCl.isVisible = boolean
        if (boolean) {
            StatusBarUtil.setTranslucentForImageView(this, 50, null)
        } else {
            StatusBarUtil.setTranslucentForImageView(this, 0, null)
        }
    }

    protected open fun init() {}

    open fun onLeftBackClick() {}

    override fun onBackPressed() {
        onLeftBackClick()
    }
}
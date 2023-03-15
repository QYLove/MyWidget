package com.sun.mywidget.ui.dot

import com.sun.base.base.BaseActivity
import com.sun.base.base.BaseProperties
import com.sun.mywidget.R
import com.sun.mywidget.databinding.ActivityDotIndicatorBinding
import com.youth.banner.listener.OnPageChangeListener

class DotIndicatorActivity:BaseActivity() {

    companion object{
        val imageUrls = listOf(
            "https://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png",
            "https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg",
            "https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg",
            "https://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg",
            "https://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png",
            "https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg",
            "https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg",
            "https://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg"
        )
    }

    override val mProperties: BaseProperties
        get() = BaseProperties(
            layoutRes = R.layout.activity_dot_indicator,
            hasTitle = true, titleStr = "点指示器",
            hasBack = true,
        )

    private val mViewBinding : ActivityDotIndicatorBinding by lazy {
        ActivityDotIndicatorBinding.bind(mViewParent)
    }

    override fun onLeftBackClick() {
        super.onLeftBackClick()
        closePage()
    }

    private fun closePage(){
        finish()
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView(){
        mViewBinding.dot.setCount(imageUrls.size)
        mViewBinding.banner.apply {
            addBannerLifecycleObserver(this@DotIndicatorActivity)
            setBannerRound(20f)
            setAdapter(ImageAdapter(imageUrls))
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(p0: Int) {
                    mViewBinding.dot.setSelectedIndex(p0)
                }

                override fun onPageScrollStateChanged(p0: Int) {
                }
            })
        }
    }
}
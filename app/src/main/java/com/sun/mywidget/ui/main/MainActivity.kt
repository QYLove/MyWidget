package com.sun.mywidget.ui.main

import androidx.activity.viewModels
import com.sun.base.base.BaseActivity
import com.sun.base.base.BaseProperties
import com.sun.mywidget.R
import com.sun.mywidget.bean.DataBean
import com.sun.mywidget.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    override val mProperties: BaseProperties
        get() = BaseProperties(
            layoutRes = R.layout.activity_main,
            hasTitle = true, titleStr = "主页",
            hasBack = true
        )

    private val mViewBinding:ActivityMainBinding by lazy {
        ActivityMainBinding.bind(mViewParent)
    }

    private val mViewModel by viewModels<MainViewModel>()

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
        val list = mutableListOf<DataBean>()
        for (i in 0 until 6){
            list.add(DataBean("数据$i",i*10))
        }
        mViewBinding.pie.setData(list)
    }
}
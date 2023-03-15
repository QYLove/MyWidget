package com.sun.mywidget.ui.pie

import com.sun.base.base.BaseActivity
import com.sun.base.base.BaseProperties
import com.sun.mywidget.R
import com.sun.mywidget.bean.DataBean
import com.sun.mywidget.databinding.ActivityPie3dViewBinding

class Pie3DViewActivity : BaseActivity() {

    override val mProperties: BaseProperties =
        BaseProperties(
            layoutRes = R.layout.activity_pie3d_view,
            hasTitle = true, titleStr = "3D饼图",
            hasBack = true,
        )

    private val mViewBinding: ActivityPie3dViewBinding by lazy {
        ActivityPie3dViewBinding.bind(mViewParent)
    }

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
        val list = mutableListOf<DataBean>()
        for (i in 0 until 6){
            list.add(DataBean("数据$i",i*10))
        }
        mViewBinding.pie.setData(list)
    }
}
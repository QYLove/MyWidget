package com.sun.mywidget

import com.sun.base.base.BaseActivity
import com.sun.base.base.BaseProperties

class MainActivity : BaseActivity() {

    override val mProperties: BaseProperties
        get() = BaseProperties(
            layoutRes = R.layout.activity_main,
            hasTitle = true, titleStr = "主页",
            hasBack = true
        )


}
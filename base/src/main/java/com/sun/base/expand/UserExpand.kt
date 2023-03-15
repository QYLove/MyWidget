package com.sun.base.expand

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.sun.base.base.BaseConfig
import com.sun.base.bean.UserBean
import com.sun.base.bean.UserIntroBean

fun getUserIntroBean(): UserIntroBean? {
    return GsonUtils.fromJson(
        SPUtils.getInstance(BaseConfig.SP_NAME).getString(BaseConfig.SP_PROFILE),
        UserIntroBean::class.java
    )
}

fun getUserBean(): UserBean? {
    return getUserIntroBean()?.sysUser
}

fun getUserId(): String? {
    return getUserBean()?.userId
}

fun getToken():String{
    return SPUtils.getInstance(BaseConfig.SP_NAME).getString(BaseConfig.SP_TOKEN)
}
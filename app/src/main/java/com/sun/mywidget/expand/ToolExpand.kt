package com.sun.mywidget.expand

import android.util.Log
import com.sun.mywidget.base.BaseConfig.TAG
import com.sun.mywidget.base.BaseConfig.printLog

fun String.logD(){
    if (printLog) Log.d(TAG,this)
}
package com.sun.base.expand

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ToastUtils
import com.sun.base.base.BaseConfig.TAG
import com.sun.base.base.BaseConfig.printLog
import com.sun.base.dialog.LoadingDialog
import java.text.DecimalFormat

fun String.logD(){
    if (printLog) Log.d(TAG,this)
}

fun String.toast() = ToastUtils.showShort(this)

fun Float.toFormatOne(): String {
    return DecimalFormat("#.0").format(this)
}

fun Context.getColor(id: Int): Int {
    return ContextCompat.getColor(this, id)
}

/**
 * dp转px
 * @param context 上下文
 * @param dipValue dp值
 * @return px值
 */
fun Context.dip2px(dipValue: Float): Int {
    val r: Resources = this.applicationContext.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dipValue, r.displayMetrics
    ).toInt()
}

fun LoadingDialog.Builder.defaultDialogView(): LoadingDialog.Builder {
    setDialogSize(
        120f, 112f
    )
    return this
}
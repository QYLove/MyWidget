package com.sun.base.expand

import android.content.Context
import android.util.TypedValue

/**
 * 将传进来的数转化为dp
 */
fun Context.convertToDp(num: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        num.toFloat(),
        resources.displayMetrics
    ).toInt()
}

/**
 * 将传进来的数转化为sp
 */
fun Context.convertToSp(num: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        num.toFloat(),
        resources.displayMetrics
    ).toInt()
}
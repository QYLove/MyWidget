package com.sun.base.expand

import android.content.Context
import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.setOnSingleClickListener(listener: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(listener))
}

class OnSingleClickListener : View.OnClickListener {
    private val listener: View.OnClickListener
    private var prevTime = 0L

    constructor(listener: (View) -> Unit) {
        this.listener = View.OnClickListener { listener.invoke(it) }
    }

    companion object {
        private const val DELAY = 500L
    }

    override fun onClick(v: View?) {
        val time = System.currentTimeMillis()
        if (time - prevTime >= DELAY) {
            prevTime = time
            listener.onClick(v)
        }
    }
}

fun View.adaptStatusBarHeight() {
    val pH = getStatusBarHeight(context)
    if (pH > 0) {
        val params = this.layoutParams
        params.height = pH
        layoutParams = params
    }
}

/**
 * get statusBar height
 *
 * @param context
 * @return statusBar height
 */
private fun getStatusBarHeight(context: Context): Int {
    try {
        var result = 0
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return 0
}
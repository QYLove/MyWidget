package com.sun.base.util

import android.content.Context
import androidx.annotation.StringRes
import com.sun.base.dialog.LoadingDialog
import com.sun.base.expand.defaultDialogView

object LoadingUtil {

    private var dialog: LoadingDialog? = null

    fun showLoading(context: Context){
        showLoading(context,"请稍等")
    }

    /**
     * 显示对话框
     */
    fun showLoading(context: Context, msg: String) {
        dialog?.apply {
            if (isShowing) {
                cancel()
            }
        }

        dialog = LoadingDialog.Builder(context)
            .defaultDialogView()
            .setBottomDesc(msg)
            .create()
        dialog!!.show()
    }

    /**
     * 显示对话框
     */
    fun showLoading(context: Context, @StringRes resId: Int) {
        dialog?.apply {
            if (isShowing) {
                cancel()
            }
        }

        dialog = LoadingDialog.Builder(context)
            .defaultDialogView()
            .setBottomDesc(context.getString(resId))
            .create()
        dialog!!.show()
    }

    /**
     * 取消
     */
    fun cancel() {
        dialog?.cancel()
    }

}
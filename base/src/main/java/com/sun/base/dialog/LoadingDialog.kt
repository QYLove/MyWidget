package com.sun.base.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.sun.base.R
import com.sun.base.expand.dip2px

class LoadingDialog private constructor(context: Context) : Dialog(context),
    LifecycleEventObserver {

    private lateinit var tv: TextView
    private lateinit var ll: LinearLayout
    private lateinit var pb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_dialog_loading)
        tv = findViewById(R.id.item_dialog_loading_tv)
        ll = findViewById(R.id.item_dialog_loading_ll)
        pb = findViewById(R.id.item_dialog_loading_pb)
        val dialogWindow = window
        dialogWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    //是否可以触摸取消 默认为false
    private var canceledOnTouchOutsideValue = false

    //是否可以点击返回 取消 默认为false
    private var canceledOnBack = true

    //背景框 默认
    private var dialogBackgroundResource = R.drawable.loading_toast

    //底部文字颜色
    private var bottomTextColor = context.getColor(R.color.bg_white)

    //底部文字大小
    private var bottomTextSize = 15f

    //弹窗布局大小 宽度
    private var dialogWidth = 0f

    //弹窗布局大小 高度
    private var dialogHeight = 0f

    //progressBar的大小
    private var progressBarSize = 0f

    //底部文字描述
    private var bottomTextDesc: String? = null

    override fun show() {
        if (ownerActivity != null && !ownerActivity!!.isFinishing) {
            super.show()
            show(this)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun show(loadingDialog: LoadingDialog) {
        if (!TextUtils.isEmpty(bottomTextDesc)) {
            tv.text = bottomTextDesc
            tv.visibility = View.VISIBLE
        }

        //屏蔽返回键
        if (!canceledOnBack) {
            setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK
            }
        }

        setCanceledOnTouchOutside(canceledOnTouchOutsideValue)

        //设置弹窗背景
        ll.setBackgroundResource(dialogBackgroundResource)

        tv.setTextColor(bottomTextColor)
        tv.textSize = bottomTextSize

        if (dialogWidth != 0f) {
            val layoutParams = ll.layoutParams
            layoutParams.width = context.dip2px(dialogWidth)
            layoutParams.height = context.dip2px(dialogHeight)
            ll.layoutParams = layoutParams
        }

        if (progressBarSize != 0f) {
            var layoutParams = pb.layoutParams
            layoutParams.width = context.dip2px(progressBarSize)
            layoutParams.height = context.dip2px(progressBarSize)
            pb.layoutParams = layoutParams
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            if (isShowing) {
                dismiss()
            }
        }
    }

    init {
        if (context is Activity) {
            setOwnerActivity(context)
        }
        if (context is ComponentActivity) {
            context.lifecycle.addObserver(this)
        }
    }

    class Builder(context: Context) {

        private var loadingDialog: LoadingDialog = LoadingDialog(context)

        //设置底部文字描述
        fun setBottomDesc(bottomDesc: String): Builder {
            loadingDialog.bottomTextDesc = bottomDesc
            return this
        }

        //设置触摸屏幕时是否消失
        fun setCanceledOnTouchOutsideValue(canceled: Boolean): Builder {
            loadingDialog.canceledOnTouchOutsideValue = canceled
            return this
        }

        //设置是否可以点击返回键
        fun setCanceledOnBackValue(onBack: Boolean): Builder {
            loadingDialog.canceledOnBack = onBack
            return this
        }

        //设置dialog背景
        fun setDialogBackground(resource: Int): Builder {
            loadingDialog.dialogBackgroundResource = resource
            return this
        }

        //设置底部文字颜色
        fun setBottomTextColor(resource: Int): Builder {
            loadingDialog.bottomTextColor = resource
            return this
        }

        //设置底部文字大小
        fun setBottomTextSize(size: Float): Builder {
            loadingDialog.bottomTextSize = size
            return this
        }

        //设置弹窗大小
        fun setDialogSize(dialogWidth: Float, dialogHeight: Float): Builder {
            loadingDialog.dialogWidth = dialogWidth
            loadingDialog.dialogHeight = dialogHeight
            return this
        }

        //设置progressBar的大小
        fun setProgressBarSize(size: Float): Builder {
            loadingDialog.progressBarSize = size
            return this
        }

        //通过Builder类设置完属性后构造对话框的方法
        fun create(): LoadingDialog = loadingDialog
    }

}
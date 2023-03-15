package com.sun.base.base

import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sun.base.R

abstract class BaseDialog(
    @LayoutRes var contentLayoutId: Int,
    private val isOnTouchOutSide: Boolean = true,
    private val isOnBackPress: Boolean = true,
    private val viewSite: Int = Gravity.CENTER,
    private val windowWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    private val windowHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    private val alpha: Float = 0.6f
):DialogFragment(contentLayoutId){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BaseDialogStyle)
    }

    override fun onStart() {
        super.onStart()
        setSite()
    }

    private fun setSite(){
        val mWindow = dialog?.window
        val mLayoutParams = mWindow?.attributes
        mLayoutParams?.width = windowWidth
        mLayoutParams?.height = windowHeight
        mLayoutParams?.gravity = viewSite
        mWindow?.attributes = mLayoutParams
        mWindow?.setDimAmount(alpha)

        dialog?.setCanceledOnTouchOutside(isOnTouchOutSide)
        dialog?.setOnKeyListener { _, keyCode, _ -> !isOnBackPress && keyCode == KeyEvent.KEYCODE_BACK }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    protected abstract fun initData()

    fun showDialog(manager:FragmentManager,tag:String){
        if (this.isResumed){
            return
        }
        showNow(manager,tag)
    }
}
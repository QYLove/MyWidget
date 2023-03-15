package com.sun.mywidget.ui.main

import com.sun.base.base.BaseFragment
import com.sun.base.expand.viewBinding
import com.sun.mywidget.R
import com.sun.mywidget.databinding.FragmentMainBinding

class MainFragment:BaseFragment(R.layout.fragment_main) {

    private val mViewBinding by viewBinding(FragmentMainBinding::bind)

    override fun init() {
        super.init()
        initView()
    }

    private fun initView(){

    }
}
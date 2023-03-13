package com.sun.base.base

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.sun.base.R

data class BaseProperties(
    @LayoutRes val layoutRes: Int,
    val hasTitle: Boolean? = false,
    @StringRes val title: Int? = null,
    val titleStr: String? = null,
    val hasBack: Boolean? = false,
    @DrawableRes val backDrawableRes: Int = R.mipmap.icon_back,
)

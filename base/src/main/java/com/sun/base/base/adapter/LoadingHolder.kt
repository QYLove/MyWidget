package com.sun.base.base.adapter

import android.view.ViewGroup
import com.sun.base.databinding.ItemLoadingProgressBinding
import com.sun.base.expand.viewBinding

class LoadingHolder(parent: ViewGroup) :
    BaseViewHolder<ItemLoadingProgressBinding, Any>(parent.viewBinding(ItemLoadingProgressBinding::inflate)) {
    override fun bindData(
        position: Int,
        item: Any?,
        onItemClick: ((position: Int, action: Any) -> Unit)?,
        onLongClick: ((position: Int, action: Any) -> Unit)?,
        onOtherClick: ((position: Int, action: Any) -> Unit)?,
        vararg params: Any?
    ) {
    }
}
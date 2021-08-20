package com.yang.lib_common.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.lib_common.R

class NormalImageAdapter<T>(list: MutableList<T>,layoutResId:Int = R.layout.item_normal_image) :
    BaseQuickAdapter<T, BaseViewHolder>(list) {
    init {
        mLayoutResId = layoutResId
    }

    override fun convert(helper: BaseViewHolder, item: T) {
        val ivImg = helper.getView<ImageView>(R.id.iv_image)
        Glide.with(ivImg)
            .load(item)
            .centerCrop()
            .into(ivImg)
    }
}
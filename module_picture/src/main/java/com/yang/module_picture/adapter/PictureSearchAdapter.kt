package com.yang.module_picture.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.yang.module_picture.R
import com.yang.module_picture.model.ImageDataItem

/**
 * @Author Administrator
 * @ClassName PictureSearchAdapter
 * @Description
 * @Date 2021/9/13 16:55
 */
class PictureSearchAdapter(layoutResId: Int, list: MutableList<ImageDataItem>): BaseQuickAdapter<ImageDataItem, BaseViewHolder>(layoutResId, list) {
    override fun convert(helper: BaseViewHolder, item: ImageDataItem) {
        val ivImage = helper.getView<ShapeableImageView>(R.id.iv_image)
        Glide.with(ivImage)
            .load(item.imageUrl)
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(ivImage)
    }
}
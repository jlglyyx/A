package com.yang.module_picture.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.yang.module_picture.R
import com.yang.module_picture.data.model.ImageDataItem

/**
 * @Author Administrator
 * @ClassName PictureAdapter
 * @Description
 * @Date 2021/9/9 11:02
 */
class PictureAdapter(layoutResId: Int, list: MutableList<ImageDataItem>): BaseQuickAdapter<ImageDataItem, BaseViewHolder>(layoutResId, list) {
    override fun convert(helper: BaseViewHolder, item: ImageDataItem) {
        val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
        helper
            .setText(R.id.tv_title,item.imageTitle)
            .setText(R.id.tv_desc,item.imageDesc)
//                .setGone(R.id.tv_title,!TextUtils.isEmpty(item.imageTitle))
//                .setGone(R.id.tv_desc,!TextUtils.isEmpty(item.imageDesc))
        Glide.with(sivImg)
            .load(item.imageUrl)
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(sivImg)
    }
}
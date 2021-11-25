package com.yang.lib_common.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.yang.lib_common.R
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.CommentData

/**
 * @Author Administrator
 * @ClassName CommentAdapter
 * @Description
 * @Date 2021/11/25 11:12
 */
class CommentAdapter(data: MutableList<CommentData>?) :
    BaseMultiItemQuickAdapter<CommentData, BaseViewHolder>(data) {

    init {
        addItemType(AppConstant.Constant.PARENT_COMMENT_TYPE, R.layout.item_comment)
        addItemType(AppConstant.Constant.CHILD_COMMENT_TYPE, R.layout.item_child_comment)
        addItemType(
            AppConstant.Constant.CHILD_REPLY_COMMENT_TYPE,
            R.layout.item_child_reply_comment
        )
    }

    override fun convert(helper: BaseViewHolder, item: CommentData) {

        helper.setText(R.id.tv_comment, item.comment)
        val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
        Glide.with(sivImg)
            .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(sivImg)


        when (item.mLevel) {
            AppConstant.Constant.PARENT_COMMENT_TYPE -> {
                helper.setGone(R.id.tv_open_comment, !item.isExpanded)
                helper.setText(R.id.tv_open_comment,"-----${item.subItems?.size}-----")
                helper.itemView.setOnClickListener {
                    if (item.isExpanded){
                        collapse(helper.layoutPosition)
                    }else{
                        expand(helper.layoutPosition)
                    }
                }
            }
            AppConstant.Constant.CHILD_COMMENT_TYPE -> {
                when (item.mItemType) {
                    AppConstant.Constant.CHILD_COMMENT_TYPE -> {

                    }
                    AppConstant.Constant.CHILD_REPLY_COMMENT_TYPE -> {
                        helper.addOnClickListener(R.id.siv_reply_img)
                        val sivReplyImg = helper.getView<ShapeableImageView>(R.id.siv_reply_img)
                        Glide.with(sivReplyImg)
                            .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=1.jpg")
                            .error(R.drawable.iv_image_error)
                            .placeholder(R.drawable.iv_image_placeholder)
                            .into(sivReplyImg)
                    }
                }
            }
        }

        helper.addOnClickListener(R.id.siv_img)
            .addOnClickListener(R.id.tv_reply)
    }
}
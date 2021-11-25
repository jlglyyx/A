package com.yang.lib_common.data

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @Author Administrator
 * @ClassName CommentData
 * @Description
 * @Date 2021/11/25 11:16
 */
class CommentData constructor(var mLevel: Int,var mItemType : Int):AbstractExpandableItem<CommentData>(),MultiItemEntity {

    var comment:String? = null

    var parentPosition:Int? = null

    override fun getLevel(): Int {

        return mLevel
    }

    override fun getItemType(): Int {

        return mItemType
    }
}
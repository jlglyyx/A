package com.yang.module_picture.data

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @Author Administrator
 * @ClassName CommonData
 * @Description
 * @Date 2021/9/8 10:29
 */
class CommonData constructor(private val itemType:Int,private val level:Int,var content:String): AbstractExpandableItem<CommonData>(), MultiItemEntity {

    override fun getItemType(): Int {
        return itemType
    }

    override fun getLevel(): Int {
        return level
    }

}
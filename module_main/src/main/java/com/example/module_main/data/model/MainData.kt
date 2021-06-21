package com.example.module_main.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity

class MainData constructor(private val itemType: Int) : MultiItemEntity {

    override fun getItemType(): Int {
        return itemType
    }


}
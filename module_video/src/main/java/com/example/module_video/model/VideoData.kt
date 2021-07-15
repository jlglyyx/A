package com.example.module_video.model

import com.chad.library.adapter.base.entity.MultiItemEntity

class VideoData constructor(private val itemType: Int) : MultiItemEntity {
    override fun getItemType(): Int {
        return itemType
    }

    var bigTitle:String? = null
    var smartTitle:String? = null
}
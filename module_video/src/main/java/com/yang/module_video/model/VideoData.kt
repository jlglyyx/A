package com.yang.module_video.model

import com.chad.library.adapter.base.entity.MultiItemEntity

data class VideoData(
    var list: List<VideoDataItem>,
    var pageNum: Int,
    var pageSize: Int,
    var size: Int,
    var total: String

)

data class VideoDataItem constructor(private val itemType: Int) : MultiItemEntity {
    override fun getItemType(): Int {
        return itemType
    }
    var select = false
    var position = 0
    var createTime: String? = null
    var id: String? = null
    var videoName: String? = null
    var videoType: String? = null
    var videoUrl: String? = null
    var videoTitle: String? = null
    var updateTime: String? = null

}


data class VideoTypeData(
    var id: Int? = 0,
    var name: String,
    var type: String,
    var extraInfo: String
)
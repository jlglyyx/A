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

    var position = 0
    val createTime: String? = null
    val id: String? = null
    val videoName: String? = null
    val videoType: String? = null
    val videoUrl: String? = null
    val videoTitle: String? = null
    val updateTime: String? = null

}


data class VideoTypeData(
    var id: Int? = 0,
    var name: String,
    var type: String,
    var extraInfo: String
)
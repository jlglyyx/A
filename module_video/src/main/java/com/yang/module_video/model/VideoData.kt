package com.yang.module_video.model

data class VideoData(
    var list: List<VideoDataItem>,
    var pageNum: Int?,
    var pageSize: Int?,
    var size: Int?,
    var total: String?

)

class VideoDataItem  {
    var select = false
    var position = 0
    var createTime: String? = null
    var id: String? = null
    var videoName: String? = null
    var videoType: String? = null
    var videoUrl: String? = null
    var videoTitle: String? = null
    var updateTime: String? = null
    var smartVideoUrls : MutableList<VideoDataItem>? = null
}


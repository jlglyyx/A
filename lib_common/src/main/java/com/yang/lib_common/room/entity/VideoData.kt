package com.yang.lib_common.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class VideoData(
    var list: List<VideoDataItem>,
    var pageNum: Int?,
    var pageSize: Int?,
    var size: Int?,
    var total: String?

)
@Entity(tableName = "video_data")
class VideoDataItem {
    @Ignore
    var select = false
    @Ignore
    var position = 0
    @ColumnInfo
    var createTime: String? = null
    @PrimaryKey
    var id: String = ""
    @ColumnInfo
    var videoName: String? = null
    @ColumnInfo
    var videoType: String? = null
    @ColumnInfo
    var videoUrl: String? = null
    @ColumnInfo
    var videoTitle: String? = null
    @ColumnInfo
    var updateTime: String? = null
    @ColumnInfo
    var isLarge: Boolean = false
    @ColumnInfo
    var itemId: String? = null
    @ColumnInfo
    var title: String? = null
    @Ignore
    var smartVideoUrls: MutableList<VideoDataItem>? = null
}


package com.yang.lib_common.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *des:
 *
 *@author My Live
 *@date 2021/6/11
 */
data class ImageData(
    val list: List<ImageDataItem>,
    val pageNum: Int?,
    val pageSize: Int?,
    val size: Int?,
    val total: String?
)

@Entity(tableName = "image_data")
data class ImageDataItem(
    @ColumnInfo
    var createTime: String?,
    @PrimaryKey
    var id: String,
    @ColumnInfo
    var imageName: String?,
    @ColumnInfo
    var imageType: String?,
    @ColumnInfo
    var imageUrl: String?,
    @ColumnInfo
    var imageDesc: String?,
    @ColumnInfo
    var imageTitle: String?,
    @ColumnInfo
    var imageExtraInfo: String?,
    @ColumnInfo
    var updateTime: String?
)
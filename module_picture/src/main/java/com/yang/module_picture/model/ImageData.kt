package com.yang.module_picture.model

/**
 *des:
 *
 *@author My Live
 *@date 2021/6/11
 */
data class ImageData(
    val list: List<ImageDataItem>,
    val pageNum: Int,
    val pageSize: Int,
    val size: Int,
    val total: String
)

data class ImageDataItem(
    var createTime: String?,
    var id: String?,
    var imageName: String,
    var imageType: String,
    var imageUrl: String,
    var updateTime: String?
)
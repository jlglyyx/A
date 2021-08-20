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
    val createTime: String,
    val id: String,
    val imageName: String,
    val imageType: String,
    val imageUrl: String,
    val updateTime: String
)
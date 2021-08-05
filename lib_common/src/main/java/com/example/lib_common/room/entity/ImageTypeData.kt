package com.example.lib_common.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *des:
 *
 *@author My Live
 *@date 2021/6/12
 */
@Entity(tableName = "image_type")
data class ImageTypeData(
    @PrimaryKey(autoGenerate = true)
    var id: Int ?= 0,
    var name: String,
    var type: String,
    var extraInfo: String
)
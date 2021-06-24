package com.example.module_main.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.text.SimpleDateFormat
import java.util.*

class MainData constructor(private val itemType: Int) : MultiItemEntity {

    private val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd hh:mm:ss")

    override fun getItemType(): Int {
        return itemType
    }


    var imageList: MutableList<String>? = null

    var dynamicContent: String? = null

    var createTime : String = simpleDateFormat.format(Date())


}
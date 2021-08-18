package com.example.module_main.data.model

import java.text.SimpleDateFormat
import java.util.*

class MainData  {

    private val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd hh:mm:ss")

    var userImage: String? = "https://img0.baidu.com/it/u=2743095369,633820497&fm=26&fmt=auto&gp=0.jpg"

    var imageList: MutableList<String>? = null

    var videoUrl: String? = null

    var dynamicContent: String? = null

    var createTime : String = simpleDateFormat.format(Date())


}
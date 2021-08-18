package com.example.module_main.data.model

import com.example.lib_common.util.simpleDateFormat
import java.util.*

class MainData {

    var id: String? = null

    var userId: String? = null

    var userImage: String? =
        "https://img0.baidu.com/it/u=2743095369,633820497&fm=26&fmt=auto&gp=0.jpg"

    var userName: String? = null

    var imageUrls: String? = null

    var videoUrls: String? = null

    var dynamicContent: String? = null

    var createTime: String = simpleDateFormat.format(Date())


}

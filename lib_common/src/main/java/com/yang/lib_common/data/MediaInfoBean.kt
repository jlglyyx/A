package com.yang.lib_common.data

/**
 * @Author Administrator
 * @ClassName MediaInfoBean
 * @Description
 * @Date 2021/8/6 14:14
 */
class MediaInfoBean {
    var fileName: String? = null
    var filePath: String? = null
    var fileCreateTime: Long? = null
    var fileDurationTime: String? = null
    var fileSize: String? = null
    /**
     * 0 图片 1 视频
     */
    var fileType:Int = -1
    var isSelect = false

    override fun toString(): String {
        return "MediaInfoBean(fileName=$fileName, filePath=$filePath, fileCreateTime=$fileCreateTime, fileDurationTime=$fileDurationTime, fileSize=$fileSize, fileType=$fileType, isSelect=$isSelect)"
    }


}
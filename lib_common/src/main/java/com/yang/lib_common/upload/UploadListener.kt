package com.yang.lib_common.upload

/**
 * @Author Administrator
 * @ClassName Upload
 * @Description
 * @Date 2021/11/19 9:23
 */
interface UploadListener {

    fun onProgress(noticeId: Int, progress: Int)

    fun onSuccess(noticeId: Int)

    fun onFailed(noticeId: Int)
}
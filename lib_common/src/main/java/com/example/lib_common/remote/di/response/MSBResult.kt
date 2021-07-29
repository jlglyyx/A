package com.example.lib_common.remote.di.response


/**
 * @ClassName Result
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/4 15:59
 */
data class MSBResult<out T : Any>(
    val data: T, val errorCode: Int, val errorMsg: String
)

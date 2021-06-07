package com.example.lib_common.data

data class MResult<out T : Any>(
    val data: T, val errorCode: Int, val errorMsg: String
)


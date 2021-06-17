package com.example.lib_common.data

data class MResult<out T : Any>(
    val data: T, val code: String, val message: String,val success : Boolean
)


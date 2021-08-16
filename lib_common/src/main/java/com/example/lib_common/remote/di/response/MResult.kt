package com.example.lib_common.remote.di.response

data class MResult<T : Any>(
    val data: T, val code: String, val message: String,val success : Boolean
)


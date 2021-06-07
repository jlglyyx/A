package com.example.lib_common.help

interface ErrorCode {

    enum class HttpExceptionCode(val code:Int,val message:String){

        NOT_FIND(404,"未找到请求地址")
    }
}
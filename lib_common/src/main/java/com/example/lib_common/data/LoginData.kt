package com.example.lib_common.data

import java.util.*

data class LoginData(
    val id: String?,
    val token: String?,
    val userName: String,
    val userAccount: String,
    val userPassword: String,
    val userPhone: String?,
    val userImage: String?,
    val userLevel: Int?,
    val updateTime: Date?,
    val createTime: Date?,
    val userExtraInfo: String?

)
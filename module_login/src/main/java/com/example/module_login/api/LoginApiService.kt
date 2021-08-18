package com.example.module_login.api

import com.example.lib_common.data.UserInfoData
import com.example.lib_common.remote.di.response.MResult
import retrofit2.http.*

interface LoginApiService {

    @POST("user/login")
    suspend fun login(@Query("userAccount") userAccount:String, @Query("password") password:String): MResult<UserInfoData>

    @POST("user/register")
    suspend fun register(@Body userInfoData: UserInfoData): MResult<UserInfoData>
}
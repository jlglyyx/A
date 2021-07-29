package com.example.module_login.api

import com.example.lib_common.remote.di.response.MResult
import com.example.lib_common.remote.di.response.MSBResult
import com.example.module_login.data.model.LoginData
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("userAccount") userAccount:String,@Field("password") password:String): MResult<LoginData>

    @POST("user/register")
    suspend fun register(@Body loginData: LoginData): MResult<LoginData>
}
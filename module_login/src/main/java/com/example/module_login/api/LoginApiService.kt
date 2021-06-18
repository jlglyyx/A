package com.example.module_login.api

import com.example.lib_common.data.MResult
import com.example.module_login.data.model.LoginData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username:String,@Field("password") password:String): MResult<LoginData>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(@Field("username") username:String, @Field("password") password:String, @Field("repassword") repassword:String): MResult<LoginData>
}
package com.yang.module_main.api

import com.yang.lib_common.remote.di.response.MResult
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.QueryMap
import com.yang.module_main.data.model.DynamicData
import okhttp3.RequestBody
import retrofit2.http.*

interface MainApiService {

    @POST("user/dynamic/insertUserDynamic")
    suspend fun addDynamic(@Body dynamicData: DynamicData): MResult<String>

    @POST("user/dynamic/queryUserDynamic")
    suspend fun getDynamicList(@QueryMap params:Map<String,String>): MResult<MutableList<DynamicData>>

    @Multipart
    @POST("/uploadFile")
    suspend fun uploadFile(@PartMap file: MutableMap<String, RequestBody>): MResult<MutableList<String>>
}
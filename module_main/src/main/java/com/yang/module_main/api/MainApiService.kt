package com.yang.module_main.api

import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_main.data.model.MainData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface MainApiService {


    @POST("wxarticle/chapters/json")
    suspend fun addDynamic(@Body mainData: MainData): MResult<String>

    @POST("wxarticle/chapters/json")
    suspend fun getDynamicList(@QueryMap params:Map<String,String>): MResult<MutableList<MainData>>

}
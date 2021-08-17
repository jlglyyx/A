package com.example.module_main.api

import com.example.lib_common.remote.di.response.MResult
import com.example.module_main.data.model.AccountList
import com.example.module_main.data.model.MainData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getMainRepository(): MResult<MutableList<AccountList>>

    @POST("wxarticle/chapters/json")
    suspend fun addDynamic(@Body mainData: MainData): MResult<String>

    @POST("wxarticle/chapters/json")
    suspend fun getDynamicList(@Query("id") id:String,@Query("pageNum") pageNum:Int): MResult<MutableList<AccountList>>

    @POST("wxarticle/chapters/json")
    suspend fun getDynamicDetail(@Query("id") id:String): MResult<AccountList>
}
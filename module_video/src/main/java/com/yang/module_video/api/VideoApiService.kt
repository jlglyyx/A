package com.yang.module_video.api

import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_video.model.AccountList
import com.yang.module_video.model.VideoData
import com.yang.module_video.model.VideoDataItem
import com.yang.module_video.model.VideoTypeData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VideoApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getVideoRepository(): MResult<MutableList<AccountList>>

    @POST("video/queryVideo")
    suspend fun getVideoInfo(@Query("type") type:String, @Query("pageNum") pageNum:Int, @Query("pageSize") pageSize:Int): MResult<VideoData>

    @POST("video/queryVideoItem")
    suspend fun getVideoItemData(@Query("sid") sid:String): MResult<MutableList<VideoDataItem>>

    @POST("video/queryVideoType")
    suspend fun getVideoTypeData(): MResult<MutableList<VideoTypeData>>
}
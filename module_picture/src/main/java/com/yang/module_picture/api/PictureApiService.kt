package com.yang.module_picture.api

import com.yang.lib_common.api.BaseApiService
import com.yang.lib_common.remote.di.response.MResult
import com.yang.lib_common.room.entity.ImageTypeData
import com.yang.module_picture.data.model.ImageData
import com.yang.module_picture.data.model.ImageDataItem
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PictureApiService : BaseApiService {

    @POST("image/queryImage")
    suspend fun getImageInfo(@QueryMap map: MutableMap<String,Any>): MResult<ImageData>

    @POST("image/queryImageItem")
    suspend fun getImageItemData(@Query("sid") sid:String): MResult<MutableList<ImageDataItem>>

    @POST("image/queryImageType")
    suspend fun getImageTypeData(): MResult<MutableList<ImageTypeData>>

}
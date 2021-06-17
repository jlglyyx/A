package com.example.module_picture.api

import com.example.lib_common.data.MResult
import com.example.module_picture.model.AccountList
import com.example.module_picture.model.ImageData
import com.example.module_picture.model.ImageDataItem
import com.example.module_picture.model.ImageTypeData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PictureApiService {

    @POST("image/queryImage")
    suspend fun getImageInfo(@Query("type") type:String,@Query("pageNum") pageNum:Int,@Query("pageSize") pageSize:Int): MResult<ImageData>

    @POST("image/queryImageItem")
    suspend fun getImageItemData(@Query("sid") sid:String): MResult<MutableList<ImageDataItem>>

    @POST("image/queryImageType")
    suspend fun getImageTypeData(): MResult<MutableList<ImageTypeData>>

}
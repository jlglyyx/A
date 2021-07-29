package com.example.module_picture.repository

import com.example.lib_common.constant.AppConstant
import com.example.lib_common.remote.di.response.MResult
import com.example.module_picture.api.PictureApiService
import com.example.module_picture.model.ImageData
import com.example.module_picture.model.ImageDataItem
import com.example.module_picture.model.ImageTypeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PictureRepository @Inject constructor(private val pictureApiService: PictureApiService) {

    suspend fun getImageInfo(type:String,pageNum:Int): MResult<ImageData> {
        return withContext(Dispatchers.IO) {
            pictureApiService.getImageInfo(type,pageNum,AppConstant.Constant.PAGE_SIZE).apply {
                if (!success){
                    throw Exception(message)
                }
            }
        }
    }
    suspend fun getImageItemData(sid:String): MResult<MutableList<ImageDataItem>> {
        return withContext(Dispatchers.IO) {
            pictureApiService.getImageItemData(sid).apply {
                if (!success){
                    throw Exception(message)
                }
            }
        }
    }
    suspend fun getImageTypeData(): MResult<MutableList<ImageTypeData>> {
        return withContext(Dispatchers.IO) {
            pictureApiService.getImageTypeData().apply {
                if (!success){
                    throw Exception(message)
                }
            }
        }
    }


}
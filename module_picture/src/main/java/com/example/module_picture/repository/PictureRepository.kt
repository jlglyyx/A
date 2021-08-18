package com.example.module_picture.repository

import com.example.lib_common.base.repository.BaseRepository
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.remote.di.response.MResult
import com.example.lib_common.room.entity.ImageTypeData
import com.example.module_picture.api.PictureApiService
import com.example.module_picture.model.ImageData
import com.example.module_picture.model.ImageDataItem
import javax.inject.Inject

class PictureRepository @Inject constructor(private val pictureApiService: PictureApiService):BaseRepository() {

    suspend fun getImageInfo(type:String,pageNum:Int): MResult<ImageData> {
        return withContextIO {
            pictureApiService.getImageInfo(type,pageNum,AppConstant.Constant.PAGE_SIZE)
        }
    }
    suspend fun getImageItemData(sid:String): MResult<MutableList<ImageDataItem>> {
        return withContextIO {
            pictureApiService.getImageItemData(sid)
        }
    }
    suspend fun getImageTypeData(): MResult<MutableList<ImageTypeData>> {
        return withContextIO {
            pictureApiService.getImageTypeData()
        }
    }


}
package com.yang.module_picture.repository

import com.yang.lib_common.base.repository.BaseRepository
import com.yang.lib_common.remote.di.response.MResult
import com.yang.lib_common.room.entity.ImageTypeData
import com.yang.module_picture.api.PictureApiService
import com.yang.lib_common.room.entity.ImageData
import com.yang.lib_common.room.entity.ImageDataItem
import javax.inject.Inject

class PictureRepository @Inject constructor(private val pictureApiService: PictureApiService):BaseRepository() {

    suspend fun getImageInfo(map: MutableMap<String,Any>): MResult<ImageData> {
        return withContextIO {
            pictureApiService.getImageInfo(map)
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
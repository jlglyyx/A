package com.example.module_video.repository

import com.example.lib_common.constant.AppConstant
import com.example.lib_common.remote.di.response.MResult
import com.example.module_video.api.VideoApiService
import com.example.module_video.model.AccountList
import com.example.module_video.model.VideoData
import com.example.module_video.model.VideoDataItem
import com.example.module_video.model.VideoTypeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoApiService: VideoApiService) {

    suspend fun getVideoRepository(): MResult<MutableList<AccountList>> {
        return withContext(Dispatchers.IO) {
            videoApiService.getVideoRepository()
        }
    }


    suspend fun getVideoInfo(type:String,pageNum:Int): MResult<VideoData> {
        return withContext(Dispatchers.IO) {
            videoApiService.getVideoInfo(type,pageNum, AppConstant.Constant.PAGE_SIZE).apply {
                if (!success){
                    throw Exception(message)
                }
            }
        }
    }
    suspend fun getVideoItemData(sid:String): MResult<MutableList<VideoDataItem>> {
        return withContext(Dispatchers.IO) {
            videoApiService.getVideoItemData(sid).apply {
                if (!success){
                    throw Exception(message)
                }
            }
        }
    }
    suspend fun getVideoTypeData(): MResult<MutableList<VideoTypeData>> {
        return withContext(Dispatchers.IO) {
            videoApiService.getVideoTypeData().apply {
                if (!success){
                    throw Exception(message)
                }
            }
        }
    }


}
package com.example.module_video.repository

import com.example.lib_common.base.repository.BaseRepository
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.remote.di.response.MResult
import com.example.module_video.api.VideoApiService
import com.example.module_video.model.AccountList
import com.example.module_video.model.VideoData
import com.example.module_video.model.VideoDataItem
import com.example.module_video.model.VideoTypeData
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoApiService: VideoApiService):BaseRepository() {

    suspend fun getVideoRepository(): MResult<MutableList<AccountList>> {
        return withContextIO {
            videoApiService.getVideoRepository()
        }
    }


    suspend fun getVideoInfo(type:String,pageNum:Int): MResult<VideoData> {
        return withContextIO {
            videoApiService.getVideoInfo(type,pageNum, AppConstant.Constant.PAGE_SIZE)
        }
    }
    suspend fun getVideoItemData(sid:String): MResult<MutableList<VideoDataItem>> {
        return withContextIO {
            videoApiService.getVideoItemData(sid)
        }
    }
    suspend fun getVideoTypeData(): MResult<MutableList<VideoTypeData>> {
        return withContextIO {
            videoApiService.getVideoTypeData()
        }
    }


}
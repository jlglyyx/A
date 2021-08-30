package com.yang.module_video.repository

import com.yang.lib_common.base.repository.BaseRepository
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_video.api.VideoApiService
import com.yang.module_video.model.AccountList
import com.yang.module_video.model.VideoData
import com.yang.module_video.model.VideoDataItem
import com.yang.module_video.model.VideoTypeData
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoApiService: VideoApiService):BaseRepository() {

    suspend fun getVideoRepository(): MResult<MutableList<AccountList>> {
        return withContextIO {
            videoApiService.getVideoRepository()
        }
    }


    suspend fun getVideoInfo(type:String,pageNum:Int): MResult<VideoData> {
        return withContextIO {
            videoApiService.getVideoInfo(type,pageNum, AppConstant.Constant.PAGE_SIZE_COUNT)
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
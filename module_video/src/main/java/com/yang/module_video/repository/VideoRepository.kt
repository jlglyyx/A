package com.yang.module_video.repository

import com.yang.lib_common.base.repository.BaseRepository
import com.yang.lib_common.remote.di.response.MResult
import com.yang.lib_common.room.entity.VideoTypeData
import com.yang.module_video.api.VideoApiService
import com.yang.module_video.model.AccountList
import com.yang.module_video.model.VideoData
import com.yang.module_video.model.VideoDataItem
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoApiService: VideoApiService):BaseRepository() {

    suspend fun getVideoRepository(): MResult<MutableList<AccountList>> {
        return withContextIO {
            videoApiService.getVideoRepository()
        }
    }


    suspend fun getVideoInfo(map: MutableMap<String,Any>): MResult<VideoData> {
        return withContextIO {
            videoApiService.getVideoInfo(map)
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
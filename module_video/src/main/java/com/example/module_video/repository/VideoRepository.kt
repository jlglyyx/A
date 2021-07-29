package com.example.module_video.repository

import com.example.lib_common.remote.di.response.MResult
import com.example.module_video.api.VideoApiService
import com.example.module_video.model.AccountList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoApiService: VideoApiService) {

    suspend fun getVideoRepository(): MResult<MutableList<AccountList>> {
        return withContext(Dispatchers.IO) {
            videoApiService.getVideoRepository()
        }
    }


}
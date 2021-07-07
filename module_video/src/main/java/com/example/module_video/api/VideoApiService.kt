package com.example.module_video.api

import com.example.lib_common.data.MResult
import com.example.module_video.model.AccountList
import retrofit2.http.GET

interface VideoApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getVideoRepository(): MResult<MutableList<AccountList>>
}
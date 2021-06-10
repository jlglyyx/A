package com.example.module_picture.api

import com.example.lib_common.data.MResult
import com.example.module_picture.model.AccountList
import retrofit2.http.GET

interface PictureApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getPictureRepository(): MResult<MutableList<AccountList>>
}
package com.example.module_main.api

import com.example.lib_common.data.MResult
import com.example.module_main.data.model.AccountList
import retrofit2.http.GET

interface MainApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getMainRepository(): MResult<MutableList<AccountList>>
}
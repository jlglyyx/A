package com.example.lib_common.api

import io.reactivex.Observable
import retrofit2.http.GET

interface BaseApiService {

    @GET("/")
    fun getA():Observable<String>
}
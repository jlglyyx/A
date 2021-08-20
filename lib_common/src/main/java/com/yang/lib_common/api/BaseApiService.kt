package com.yang.lib_common.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface BaseApiService {

    @GET("/")
    fun getA(): Observable<String>
}
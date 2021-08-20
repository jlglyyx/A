package com.yang.module_main.repository

import com.yang.lib_common.base.repository.BaseRepository
import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_main.api.MainApiService
import com.yang.module_main.data.model.MainData
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiService: MainApiService) :
    BaseRepository() {


    suspend fun addDynamic(mainData: MainData): MResult<String> {
        return withContextIO {
            mainApiService.addDynamic(mainData)
        }
    }

    suspend fun getDynamicList(params: Map<String, String>): MResult<MutableList<MainData>> {
        return withContextIO {
            mainApiService.getDynamicList(params)
        }
    }



}
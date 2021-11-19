package com.yang.module_main.repository

import com.yang.lib_common.base.repository.BaseRepository
import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_main.api.MainApiService
import com.yang.module_main.data.model.DynamicData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiService: MainApiService) :
    BaseRepository() {


    suspend fun addDynamic(dynamicData: DynamicData): MResult<String> {
        return withContextIO {
            mainApiService.addDynamic(dynamicData)
        }
    }

    suspend fun getDynamicList(params: Map<String, String>): MResult<MutableList<DynamicData>> {
        return withContextIO {
            mainApiService.getDynamicList(params)
        }
    }
    suspend fun uploadFile(filePaths: MutableMap<String, RequestBody>): MResult<MutableList<String>> {
        return withContext(Dispatchers.IO) {
            mainApiService.uploadFile(filePaths)
        }
    }
    suspend fun uploadFileAndParam(filePaths: MutableList<RequestBody>): MResult<MutableList<String>> {
        return withContext(Dispatchers.IO) {
            mainApiService.uploadFileAndParam(filePaths)
        }
    }


}
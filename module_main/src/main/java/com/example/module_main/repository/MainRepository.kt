package com.example.module_main.repository

import com.example.lib_common.remote.di.response.MResult
import com.example.module_main.api.MainApiService
import com.example.module_main.data.model.AccountList
import com.example.module_main.data.model.MainData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiService: MainApiService) {

    suspend fun getMainRepository(): MResult<MutableList<AccountList>> {
        return withContext(Dispatchers.IO) {
            mainApiService.getMainRepository()
        }
    }
    suspend fun addDynamic(mainData: MainData): MResult<String> {
        return withContext(Dispatchers.IO) {
            mainApiService.addDynamic(mainData)
        }
    }
    suspend fun getDynamicList(id:String,pageNum:Int): MResult<MutableList<AccountList>> {
        return withContext(Dispatchers.IO) {
            mainApiService.getDynamicList(id,pageNum)
        }
    }
    suspend fun getDynamicDetail(id:String): MResult<AccountList> {
        return withContext(Dispatchers.IO) {
            mainApiService.getDynamicDetail(id)
        }
    }


}
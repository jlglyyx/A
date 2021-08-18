package com.example.module_main.repository

import com.example.lib_common.base.repository.BaseRepository
import com.example.lib_common.remote.di.response.MResult
import com.example.module_main.api.MainApiService
import com.example.module_main.data.model.AccountList
import com.example.module_main.data.model.MainData
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiService: MainApiService) :
    BaseRepository() {

    suspend fun getMainRepository(): MResult<MutableList<AccountList>> {
        return withContextIO {
            mainApiService.getMainRepository()
        }
    }

    suspend fun addDynamic(mainData: MainData): MResult<String> {
        return withContextIO {
            mainApiService.addDynamic(mainData)
        }
    }

    suspend fun getDynamicList(id: String, pageNum: Int): MResult<MutableList<AccountList>> {
        return withContextIO {
            mainApiService.getDynamicList(id, pageNum)
        }
    }

    suspend fun getDynamicDetail(params: Map<String, Any>): MResult<AccountList> {
        return withContextIO {
            mainApiService.getDynamicDetail(params)
        }
    }


}
package com.example.module_main.repository

import com.example.lib_common.data.MResult
import com.example.module_main.api.MainApiService
import com.example.module_main.data.model.AccountList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiService: MainApiService) {

    suspend fun getMainRepository(): MResult<MutableList<AccountList>> {
        return withContext(Dispatchers.IO) {
            mainApiService.getMainRepository()
        }
    }


}
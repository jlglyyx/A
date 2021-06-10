package com.example.module_picture.repository

import com.example.lib_common.data.MResult
import com.example.module_picture.api.PictureApiService
import com.example.module_picture.model.AccountList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PictureRepository @Inject constructor(private val pictureApiService: PictureApiService) {

    suspend fun getPictureRepository(): MResult<MutableList<AccountList>> {
        return withContext(Dispatchers.IO) {
            pictureApiService.getPictureRepository()
        }
    }


}
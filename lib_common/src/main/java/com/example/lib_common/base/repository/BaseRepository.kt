package com.example.lib_common.base.repository

import com.example.lib_common.data.MResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T : Any> withContextIO(mResult:suspend () ->MResult<T>): MResult<T> {
        return withContext(Dispatchers.IO) {
                mResult().apply {
                    if (errorCode == -1) {
                        throw Exception(errorMsg)
                    }
                }
            }
        }
}
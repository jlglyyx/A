package com.example.lib_common.base.repository

import com.example.lib_common.remote.di.response.MResult
import com.example.lib_common.remote.di.response.MSBResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

//    suspend fun <T : Any> withContextIO(mResult:suspend () -> MSBResult<T>): MSBResult<T> {
//        return withContext(Dispatchers.IO) {
//                mResult().apply {
//                    if (errorCode == -1) {
//                        throw Exception(errorMsg)
//                    }
//                }
//            }
//        }
    suspend fun <T : Any> withContextIO(mResult:suspend () -> MResult<T>): MResult<T> {
        return withContext(Dispatchers.IO) {
                mResult().apply {
                    if (!success) {
                        throw Exception(message)
                    }
                }
            }
        }
}
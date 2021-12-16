package com.yang.module_mine.repository

import com.yang.lib_common.base.repository.BaseRepository
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_mine.api.MineApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject

class MineRepository @Inject constructor(private val mineApiService: MineApiService) : BaseRepository() {

    suspend fun login(userAccount: String, password: String): MResult<UserInfoData> {

        return withContextIO{
            mineApiService.login(userAccount, password)
        }
    }

    suspend fun register(userInfoData: UserInfoData): MResult<UserInfoData> {

        return withContextIO{
            mineApiService.register(userInfoData)
        }

    }

    suspend fun uploadFile(filePaths: MutableMap<String, RequestBody>): MResult<MutableList<String>> {
        return withContext(Dispatchers.IO) {
            mineApiService.uploadFile(filePaths)
        }
    }

    suspend fun changePassword(password: String): MResult<String> {
        return withContextIO{
            mineApiService.changePassword(password)
        }
    }
    suspend fun changeUserInfo(userInfoData: UserInfoData): MResult<UserInfoData> {
        return withContextIO{
            mineApiService.changeUserInfo(userInfoData)
        }
    }

}
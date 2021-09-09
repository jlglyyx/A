package com.yang.module_mine.repository

import com.yang.lib_common.base.repository.BaseRepository
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.remote.di.response.MResult
import com.yang.module_mine.api.MineApiService
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


}
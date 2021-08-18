package com.example.module_login.repository

import com.example.lib_common.base.repository.BaseRepository
import com.example.lib_common.data.UserInfoData
import com.example.lib_common.remote.di.response.MResult
import com.example.module_login.api.LoginApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginApiService: LoginApiService) : BaseRepository() {

    suspend fun login(userAccount: String, password: String): MResult<UserInfoData> {

        return withContextIO{
            loginApiService.login(userAccount, password)
        }

    }

    suspend fun register(userInfoData: UserInfoData): MResult<UserInfoData> {

        return withContextIO{
            loginApiService.register(userInfoData)
        }

    }


}
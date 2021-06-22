package com.example.module_login.repository

import com.example.lib_common.base.repository.BaseRepository
import com.example.lib_common.remote.di.response.MResult
import com.example.lib_common.remote.di.response.MSBResult
import com.example.module_login.api.LoginApiService
import com.example.module_login.data.model.LoginData
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginApiService: LoginApiService) : BaseRepository() {

    suspend fun login(userAccount: String, password: String): MResult<LoginData> {

        return withContextIO{loginApiService.login(userAccount, password)}

    }

    suspend fun register(loginData: LoginData): MResult<LoginData> {

        return withContextIO{loginApiService.register(loginData)}

    }


}
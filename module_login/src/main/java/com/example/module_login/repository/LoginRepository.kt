package com.example.module_login.repository

import com.example.lib_common.base.repository.BaseRepository
import com.example.lib_common.data.MResult
import com.example.module_login.api.LoginApiService
import com.example.module_login.data.model.LoginData
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginApiService: LoginApiService) : BaseRepository() {

    suspend fun login(username: String, password: String): MResult<LoginData> {

        return withContextIO{loginApiService.login(username, password)}

    }

    suspend fun register(username: String, password: String, repassword: String): MResult<LoginData> {

        return withContextIO{loginApiService.register(username, password, repassword)}

    }


}
package com.example.module_login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.module_login.data.model.LoginData
import com.example.module_login.repository.LoginRepository
import javax.inject.Inject

/**
 * ClassName: LoginViewModel.
 * Created by Administrator on 2021/4/1_15:15.
 * Describe:
 */
class LoginViewModel @Inject constructor(
    application: Application,
    private val loginRepository: LoginRepository
) : BaseViewModel(application) {

    var mLoginData = MutableLiveData<LoginData>()

    fun login(username: String, password: String) {
        launch({
            loginRepository.login(username,password)
        }, {
            mLoginData.postValue(it.data)
        }, messages = *arrayOf("请求中...","请求成功..."))
    }
    fun register(username: String, password: String, repassword: String) {
        launch({
            loginRepository.register(username,password,repassword)
        }, {
            mLoginData.postValue(it.data)
        }, messages = *arrayOf("请求中...","请求成功..."))
    }

}


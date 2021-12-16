package com.yang.module_login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getDefaultMMKV
import com.yang.module_login.repository.LoginRepository
import java.util.*
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

    var mUserInfoData = MutableLiveData<UserInfoData>()

    fun login(userAccount: String, password: String) {
        launch({
            loginRepository.login(userAccount, password)
        }, {
            getDefaultMMKV().encode(AppConstant.Constant.TOKEN, it.data.token)
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayShowDialog()
            dismissDialog()
        },{
            getDefaultMMKV().encode(AppConstant.Constant.TOKEN, UUID.randomUUID().toString())
        }, messages = *arrayOf("请求中..."))
    }
    fun splashLogin(userAccount: String, password: String) {
        launch({
            loginRepository.login(userAccount, password)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayShowDialog()
            dismissDialog()
        },{
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
            finishActivity()
        })
    }

    fun register(userInfoData: UserInfoData) {
        launch({
            loginRepository.register(userInfoData)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayShowDialog()
            dismissDialog()
        }, messages = *arrayOf("请求中..."))
    }

}


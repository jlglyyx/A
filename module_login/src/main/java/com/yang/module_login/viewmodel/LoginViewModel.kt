package com.yang.module_login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getDefaultMMKV
import com.yang.module_login.R
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
            delayMissDialog()
        },{
            getDefaultMMKV().encode(AppConstant.Constant.TOKEN, UUID.randomUUID().toString())
        }, messages = *arrayOf(getString(R.string.string_login_ing),getString(R.string.string_login_success)))
    }
    fun splashLogin(userAccount: String, password: String) {
        launch({
            loginRepository.login(userAccount, password)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayMissDialog()
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
            delayMissDialog()
        }, messages = *arrayOf(getString(R.string.string_register_ing),getString(R.string.string_register_success)))
    }

}


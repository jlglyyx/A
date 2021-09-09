package com.yang.module_mine.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.util.buildARouter
import com.yang.module_mine.repository.MineRepository
import javax.inject.Inject

/**
 * ClassName: LoginViewModel.
 * Created by Administrator on 2021/4/1_15:15.
 * Describe:
 */
class MineViewModel @Inject constructor(
    application: Application,
    private val mineRepository: MineRepository
) : BaseViewModel(application) {

    var mUserInfoData = MutableLiveData<UserInfoData>()

    fun login(userAccount: String, password: String) {
        launch({
            mineRepository.login(userAccount, password)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayShowDialog()
            dismissDialog()
        },{
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
        }, messages = *arrayOf("请求中..."))
    }

    fun register(userInfoData: UserInfoData) {
        launch({
            mineRepository.register(userInfoData)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayShowDialog()
            dismissDialog()
        }, messages = *arrayOf("请求中..."))
    }

}


package com.yang.module_mine.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.util.buildARouter
import com.yang.module_mine.repository.MineRepository
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder
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
    var pictureListLiveData = MutableLiveData<MutableList<String>>()

    fun login(userAccount: String, password: String) {
        launch({
            mineRepository.login(userAccount, password)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayMissDialog()
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
            delayMissDialog()
        }, messages = *arrayOf("请求中..."))
    }

    fun uploadFile(filePaths: MutableList<String>) {
        launch({
            val mutableMapOf = mutableMapOf<String, RequestBody>()
            filePaths.forEach {
                val file = File(it)
                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val encode =
                    URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", "UTF-8")
                mutableMapOf["file\";filename=\"$encode"] = requestBody
            }
            mineRepository.uploadFile(mutableMapOf)
        }, {
            pictureListLiveData.postValue(it.data)
        }, messages = *arrayOf("上传中", "添加成功"))
    }

    fun changePassword(password: String) {
        launch({
            mineRepository.changePassword(password)
        }, {
            requestSuccess()
            showDialog(it.message)
            delayMissDialog()
        },{
            requestSuccess()
        },messages = *arrayOf("请求中..."))
    }
    fun changeUserInfo(userInfoData: UserInfoData) {
        launch({
            mineRepository.changeUserInfo(userInfoData)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayMissDialog()
        },messages = *arrayOf("请求中..."))
    }
}


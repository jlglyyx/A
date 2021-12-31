package com.yang.module_mine.viewmodel

import android.app.Application
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.UserInfoData
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.filterEmptyFile
import com.yang.lib_common.util.getFilePath
import com.yang.module_mine.data.MineTurnoverData
import com.yang.module_mine.data.ViewHistoryData
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

    var mViewHistoryListLiveData = MutableLiveData<MutableList<ViewHistoryData>>()

    var mMineTurnoverListLiveData = MutableLiveData<MutableList<MineTurnoverData>>()


    fun login(userAccount: String, password: String) {
        launch({
            mineRepository.login(userAccount, password)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayMissDialog()
        }, {
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
        }, {
            requestSuccess()
        }, messages = *arrayOf("请求中..."))
    }

    fun changeUserInfo(userInfoData: UserInfoData) {
        launch({
            mineRepository.changeUserInfo(userInfoData)
        }, {
            mUserInfoData.postValue(it.data)
            showDialog(it.message)
            delayMissDialog()
        }, messages = *arrayOf("请求中..."))
    }

    fun queryViewHistory() {
        launch({
            mineRepository.queryViewHistory()
        }, {
            mViewHistoryListLiveData.postValue(it.data)
            showDialog(it.message)
            delayMissDialog()
        }, {
            val mutableListOf = mutableListOf<ViewHistoryData>()
            val picturePath = getFilePath().filterEmptyFile()

            picturePath.forEach {
                mutableListOf.add(ViewHistoryData("1", it))
            }
            val videoPath =
                getFilePath("${Environment.getExternalStorageDirectory()}/MFiles/video").filterEmptyFile()
            videoPath.forEach {
                mutableListOf.add(ViewHistoryData("2", it))
            }
            mViewHistoryListLiveData.postValue(mutableListOf)
        }, errorDialog = false)
    }

    fun queryObtainTurnover(pageNum:Int) {
        launch({
            mineRepository.queryObtainTurnover(pageNum)
        }, {
            mMineTurnoverListLiveData.postValue(it.data)
//            showDialog(it.message)
//            delayMissDialog()
        },{
            mMineTurnoverListLiveData.postValue(mutableListOf<MineTurnoverData>().apply {
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
            })
        },errorDialog = false)
    }

    fun querySignTurnover() {
        launch({
            mineRepository.querySignTurnover()
        }, {
            mMineTurnoverListLiveData.postValue(it.data)
//            showDialog(it.message)
//            delayMissDialog()
        }, {
            mMineTurnoverListLiveData.postValue(mutableListOf<MineTurnoverData>().apply {
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
            })
        }, errorDialog = false)
    }

    fun queryExtensionTurnover() {
        launch({
            mineRepository.queryExtensionTurnover()
        }, {
            mMineTurnoverListLiveData.postValue(it.data)
//            showDialog(it.message)
//            delayMissDialog()
        }, {
            mMineTurnoverListLiveData.postValue(mutableListOf<MineTurnoverData>().apply {
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
                add(MineTurnoverData("签到了一天","100","+100"))
                add(MineTurnoverData("兑换了一块钱","99","-1"))
            })
        }, errorDialog = false)
    }

}


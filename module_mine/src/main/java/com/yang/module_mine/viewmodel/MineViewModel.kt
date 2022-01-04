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
import com.yang.module_mine.data.*
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

    var mViewHistoryListLiveData = MutableLiveData<MutableList<MineViewHistoryData>>()

    var mMineExtensionTurnoverListLiveData = MutableLiveData<MutableList<MineExtensionTurnoverData>>()

    var mMineObtainTurnoverListLiveData = MutableLiveData<MutableList<MineObtainTurnoverData>>()

    var mMineSignTurnoverListLiveData = MutableLiveData<MutableList<MineSignTurnoverData>>()

    var mMineGoodsDetailListLiveData = MutableLiveData<MutableList<MineGoodsDetailData>>()


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
            val mutableListOf = mutableListOf<MineViewHistoryData>()
            val picturePath = getFilePath().filterEmptyFile()

            picturePath.forEach {
                mutableListOf.add(MineViewHistoryData("1", it))
            }
            val videoPath =
                getFilePath("${Environment.getExternalStorageDirectory()}/MFiles/video").filterEmptyFile()
            videoPath.forEach {
                mutableListOf.add(MineViewHistoryData("2", it))
            }
            mViewHistoryListLiveData.postValue(mutableListOf)
        }, errorDialog = false)
    }

    fun queryObtainTurnover(pageNum: Int) {
        launch({
            mineRepository.queryObtainTurnover(pageNum)
        }, {
            mMineObtainTurnoverListLiveData.postValue(it.data)
//            showDialog(it.message)
//            delayMissDialog()
        }, {
            mMineObtainTurnoverListLiveData.postValue(mutableListOf<MineObtainTurnoverData>().apply {
                add(MineObtainTurnoverData("签到了一天", "100", "+100"))
                add(MineObtainTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineObtainTurnoverData("签到了一天", "100", "+100"))
                add(MineObtainTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineObtainTurnoverData("签到了一天", "100", "+100"))
                add(MineObtainTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineObtainTurnoverData("签到了一天", "100", "+100"))
                add(MineObtainTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineObtainTurnoverData("签到了一天", "100", "+100"))
                add(MineObtainTurnoverData("兑换了一块钱", "99", "-1"))
            })
        }, errorDialog = false)
    }

    fun querySignTurnover() {
        launch({
            mineRepository.querySignTurnover()
        }, {
            mMineSignTurnoverListLiveData.postValue(it.data)
//            showDialog(it.message)
//            delayMissDialog()
        }, {
            mMineSignTurnoverListLiveData.postValue(mutableListOf<MineSignTurnoverData>().apply {
                add(MineSignTurnoverData("签到了一天", "100", "+100"))
                add(MineSignTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineSignTurnoverData("签到了一天", "100", "+100"))
                add(MineSignTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineSignTurnoverData("签到了一天", "100", "+100"))
                add(MineSignTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineSignTurnoverData("签到了一天", "100", "+100"))
                add(MineSignTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineSignTurnoverData("签到了一天", "100", "+100"))
                add(MineSignTurnoverData("兑换了一块钱", "99", "-1"))
            })
        }, errorDialog = false)
    }

    fun queryExtensionTurnover() {
        launch({
            mineRepository.queryExtensionTurnover()
        }, {
            mMineExtensionTurnoverListLiveData.postValue(it.data)
//            showDialog(it.message)
//            delayMissDialog()
        }, {
            mMineExtensionTurnoverListLiveData.postValue(mutableListOf<MineExtensionTurnoverData>().apply {
                add(MineExtensionTurnoverData("签到了一天", "100", "+100"))
                add(MineExtensionTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineExtensionTurnoverData("签到了一天", "100", "+100"))
                add(MineExtensionTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineExtensionTurnoverData("签到了一天", "100", "+100"))
                add(MineExtensionTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineExtensionTurnoverData("签到了一天", "100", "+100"))
                add(MineExtensionTurnoverData("兑换了一块钱", "99", "-1"))
                add(MineExtensionTurnoverData("签到了一天", "100", "+100"))
                add(MineExtensionTurnoverData("兑换了一块钱", "99", "-1"))
            })
        }, errorDialog = false)
    }






















    fun queryGoodsList() {
        launch({
            mineRepository.queryGoodsList()
        }, {
            mMineGoodsDetailListLiveData.postValue(it.data)
//            showDialog(it.message)
//            delayMissDialog()
        }, {
            mMineGoodsDetailListLiveData.postValue(mutableListOf<MineGoodsDetailData>().apply {
                add(MineGoodsDetailData("", "", ""))
                add(MineGoodsDetailData("", "", ""))
                add(MineGoodsDetailData("", "", ""))
                add(MineGoodsDetailData("", "", ""))
                add(MineGoodsDetailData("", "", ""))
                add(MineGoodsDetailData("", "", ""))
                add(MineGoodsDetailData("", "", ""))
                add(MineGoodsDetailData("", "", ""))
            })
        }, errorDialog = false)
    }

}


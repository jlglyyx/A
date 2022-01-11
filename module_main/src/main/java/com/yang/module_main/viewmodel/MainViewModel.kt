package com.yang.module_main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.bus.event.LiveDataBus
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.MediaInfoBean
import com.yang.lib_common.util.toJson
import com.yang.module_main.R
import com.yang.module_main.data.model.DynamicData
import com.yang.module_main.repository.MainRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder
import javax.inject.Inject


/**
 * ClassName: MainViewModel.
 * Created by Administrator on 2021/4/1_15:15.
 * Describe:
 */
class MainViewModel @Inject constructor(
    application: Application,
    private val mainRepository: MainRepository
) : BaseViewModel(application) {

    var dynamicListLiveData = MutableLiveData<MutableList<DynamicData>>()

    var pictureListLiveData = MutableLiveData<MutableList<String>>()

    var pictureCollectListLiveData = MutableLiveData<MutableList<String>>()

    fun addDynamic(dynamicData: DynamicData) {
        launch({
            mainRepository.addDynamic(dynamicData)
        }, {
            LiveDataBus.instance.with("refresh_dynamic").value = "refresh_dynamic"
            finishActivity()
        }, messages = *arrayOf(getString(R.string.string_insert_dynamic_ing), getString(R.string.string_insert_dynamic_success), getString(R.string.string_insert_dynamic_fail)))
    }

    fun getDynamicList(params: Map<String, String>) {
        launch({
            mainRepository.getDynamicList(params)
        }, {
            dynamicListLiveData.postValue(it.data)
        }, {
//            showRecyclerViewErrorEvent()
//            cancelRefreshLoadMore()
            val mutableListOf = mutableListOf<DynamicData>()

            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img2.baidu.com/it/u=3583098839,704145971&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg" +
                            "#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
            })
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
            })
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4#http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4#http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
            })
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
            })
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "https://img1.baidu.com/it/u=3222474767,386356710&fm=26&fmt=auto#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg#https://img2.baidu.com/it/u=3583098839,704145971&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889"
            })
            dynamicListLiveData.postValue(mutableListOf)
        }, errorDialog = false)
    }

    fun getDynamicDetail(params: Map<String, String>) {
        launch({
            mainRepository.getDynamicList(params)
        }, {
            dynamicListLiveData.postValue(it.data)
        }, {
            val mutableListOf = mutableListOf<DynamicData>()
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
            })
            dynamicListLiveData.postValue(mutableListOf)
        }, messages = *arrayOf(getString(R.string.string_requesting)))
    }

    fun uploadFile(filePaths: MutableList<MediaInfoBean>) {
        launch({
            val mutableMapOf = mutableMapOf<String, RequestBody>()
            filePaths.forEach {
                val file = File(it.filePath.toString())
                val requestBody = RequestBody.create(MediaType.parse(AppConstant.ClientInfo.CONTENT_TYPE), file)
                val encode =
                    URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", AppConstant.ClientInfo.UTF_8)
                mutableMapOf["file\";filename=\"$encode"] = requestBody
            }
            mainRepository.uploadFile(mutableMapOf)
        }, {
            pictureListLiveData.postValue(it.data)
        }, messages = *arrayOf(getString(R.string.string_uploading), getString(R.string.string_insert_success)))
    }

    fun uploadFileAndParam(filePaths: MutableList<MediaInfoBean>) {
        launch({
            val mutableListOf = mutableListOf<RequestBody>()
            filePaths.forEach {
                val file = File(it.filePath.toString())
                val requestBody = RequestBody.create(MediaType.parse(AppConstant.ClientInfo.CONTENT_TYPE), file)
                val encode =
                    URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", AppConstant.ClientInfo.UTF_8)
                val build = MultipartBody.Builder()
                    .addFormDataPart("param", "".toJson())
                    .addFormDataPart("file", encode, requestBody).build()
                mutableListOf.add(build)
            }

            mainRepository.uploadFileAndParam(mutableListOf)
        }, {
            pictureListLiveData.postValue(it.data)
        }, messages = *arrayOf(getString(R.string.string_uploading), getString(R.string.string_insert_success)))
    }

    fun insertComment(params: Map<String, String>) {
        launch({
            mainRepository.insertComment(params)
        }, {

        })
    }

    fun queryCollect(type: String,pageSize: Int) {
        launch({
            mainRepository.queryCollect(type,pageSize,AppConstant.Constant.PAGE_SIZE_COUNT)
        }, {
            pictureCollectListLiveData.postValue(it.data)
        },{
            cancelRefreshLoadMore()
            showRecyclerViewErrorEvent()
        },errorDialog = false)
    }

    fun loginOut(){
        launch({
            mainRepository.loginOut()
        },{
            requestSuccess()
        },{
            requestSuccess()
        },messages = *arrayOf(getString(R.string.string_login_out_ing), getString(R.string.string_login_out_success)))
    }

}


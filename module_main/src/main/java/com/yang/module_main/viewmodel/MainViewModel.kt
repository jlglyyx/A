package com.yang.module_main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.bus.event.LiveDataBus
import com.yang.lib_common.data.MediaInfoBean
import com.yang.lib_common.util.toJson
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

    fun addDynamic(dynamicData: DynamicData) {
        launch({
            mainRepository.addDynamic(dynamicData)
        }, {
            LiveDataBus.instance.with("refresh_dynamic").value = "refresh_dynamic"
            finishActivity()
        },{
            LiveDataBus.instance.with("refresh_dynamic").value = "refresh_dynamic"
            finishActivity()
        }, messages = *arrayOf("正在努力发表中...", "发表成功"))
    }

    fun getDynamicList(params: Map<String, String>) {
        launch({
            mainRepository.getDynamicList(params)
        }, {
            dynamicListLiveData.postValue(it.data)
        }, {
            showRecyclerViewErrorEvent()
            cancelRefreshLoadMore()
            val mutableListOf = mutableListOf<DynamicData>()
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
            })
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg#https://img2.baidu.com/it/u=3583098839,704145971&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
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
            mutableListOf.add(DynamicData().apply {
                imageUrls =
                    "https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg#https://img2.baidu.com/it/u=3583098839,704145971&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg#https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
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
        },messages = *arrayOf("请求中"))
    }

    fun uploadFile(filePaths: MutableList<MediaInfoBean>) {
        launch({
            val mutableMapOf = mutableMapOf<String, RequestBody>()
            filePaths.forEach {
                val file = File(it.filePath.toString())
                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val encode = URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", "UTF-8")
                mutableMapOf["file\";filename=\"$encode"] = requestBody
            }
            mainRepository.uploadFile(mutableMapOf)
        }, {
            pictureListLiveData.postValue(it.data)
        }, messages = *arrayOf("上传中", "添加成功"))
    }
    fun uploadFileAndParam(filePaths: MutableList<MediaInfoBean>) {
        launch({
            val mutableListOf = mutableListOf<RequestBody>()
            filePaths.forEach {
                val file = File(it.filePath.toString())
                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val encode = URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", "UTF-8")
                val build = MultipartBody.Builder()
                    .addFormDataPart("param","".toJson())
                    .addFormDataPart("file",encode,requestBody).build()
                mutableListOf.add(build)
            }

            mainRepository.uploadFileAndParam(mutableListOf)
        }, {
            pictureListLiveData.postValue(it.data)
        }, messages = *arrayOf("上传中", "添加成功"))
    }


}


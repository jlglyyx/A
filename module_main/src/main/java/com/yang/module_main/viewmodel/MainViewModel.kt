package com.yang.module_main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.data.MediaInfoBean
import com.yang.module_main.data.model.DynamicData
import com.yang.module_main.repository.MainRepository
import okhttp3.MediaType
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
            dynamicListLiveData.postValue(mutableListOf)
        }, errorDialog = false)
    }

    fun uploadFile(filePaths: MutableList<MediaInfoBean>) {
        launch({
            val mutableMapOf = mutableMapOf<String, RequestBody>()
            filePaths.forEach {
                val file = File(it.filePath.toString())
                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val encode =
                    URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", "UTF-8")
                mutableMapOf["file\";filename=\"$encode"] = requestBody
            }
            mainRepository.uploadFile(mutableMapOf)
        }, {
            pictureListLiveData.postValue(it.data)
        }, messages = *arrayOf("上传中", "添加成功"))
    }


}


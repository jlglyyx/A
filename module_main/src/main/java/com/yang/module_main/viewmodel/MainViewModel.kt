package com.yang.module_main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.data.MediaInfoBean
import com.yang.module_main.data.model.DynamicData
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.module_main.repository.MainRepository
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

    fun addDynamic(dynamicData: DynamicData) {
        launch({
            mainRepository.addDynamic(dynamicData)
        }, {
            finishActivity()
        },messages = *arrayOf("正在努力发表中...","发表成功"))
    }
    fun getDynamicList(params: Map<String, String>) {
        launch({
            mainRepository.getDynamicList(params)
        }, {
            dynamicListLiveData.postValue(it.data)
        },{
            cancelRefreshLoadMore()
        },errorDialog = false)
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
            //dynamicDetailLiveData.postValue(it.data)
            showDialog("添加成功")
        })
    }



}


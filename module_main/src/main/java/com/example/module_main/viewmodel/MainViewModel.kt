package com.example.module_main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.module_main.data.model.MainData
import com.example.module_main.repository.MainRepository
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

    var dynamicListLiveData = MutableLiveData<MutableList<MainData>>()

    fun addDynamic(mainData: MainData) {
        launch({
            mainRepository.addDynamic(mainData)
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
        })
    }


//    val mutableMapOf = mutableMapOf<String, RequestBody>()
//    list.forEach {
//        val file = File(it)
//        val encode = URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", "UTF-8")
//        val create = RequestBody.create(MediaType.parse("multipart/form-data"), file)
//        mutableMapOf["file\";filename=\"$encode"] = create
//    }
}


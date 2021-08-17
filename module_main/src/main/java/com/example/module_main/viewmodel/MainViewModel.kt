package com.example.module_main.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.module_main.data.model.AccountList
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

    var sMutableLiveData = MutableLiveData<MutableList<AccountList>>()

    var dynamicListLiveData = MutableLiveData<MutableList<AccountList>>()

    var dynamicDetailLiveData = MutableLiveData<AccountList>()

    fun getMainRepository() {
        launch({
            mainRepository.getMainRepository()
        }, {
            sMutableLiveData.postValue(it.data)
        })
    }

    fun addDynamic(mainData: MainData) {
        launch({
            mainRepository.addDynamic(mainData)
        }, {
            showDialog("添加成功")
        })
    }
    fun getDynamicList(id:String,pageNum:Int) {
        launch({
            mainRepository.getDynamicList(id,pageNum)
        }, {
            dynamicListLiveData.postValue(it.data)
            showDialog("添加成功")
        })
    }

    fun getDynamicDetail(id:String) {
        launch({
            mainRepository.getDynamicDetail(id)
        }, {
            dynamicDetailLiveData.postValue(it.data)
            showDialog("添加成功")
        })
    }


}


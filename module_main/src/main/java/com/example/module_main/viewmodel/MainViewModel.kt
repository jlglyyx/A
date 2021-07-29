package com.example.module_main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.module_main.data.model.AccountList
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

    fun getMainRepository() {
        launch({
            mainRepository.getMainRepository()
        }, {
            sMutableLiveData.postValue(it.data)
        })
    }

}


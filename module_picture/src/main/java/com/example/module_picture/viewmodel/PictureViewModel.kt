package com.example.module_picture.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.module_picture.model.AccountList
import com.example.module_picture.repository.PictureRepository
import javax.inject.Inject

/**
 * ClassName: MainViewModel.
 * Created by Administrator on 2021/4/1_15:15.
 * Describe:
 */
class PictureViewModel @Inject constructor(
    application: Application,
    private val pictureRepository: PictureRepository
) : BaseViewModel(application) {

    var sMutableLiveData = MutableLiveData<MutableList<AccountList>>()

    fun getPictureRepository() {
        launch({
            pictureRepository.getPictureRepository()
        }, {
            sMutableLiveData.postValue(it.data)
        }, messages = *arrayOf("请求中...","请求成功...","请求失败..."))
    }

}


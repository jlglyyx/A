package com.example.module_video.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.module_video.model.AccountList
import com.example.module_video.repository.VideoRepository
import javax.inject.Inject

/**
 * ClassName: MainViewModel.
 * Created by Administrator on 2021/4/1_15:15.
 * Describe:
 */
class VideoViewModel @Inject constructor(
    application: Application,
    private val videoRepository: VideoRepository
) : BaseViewModel(application) {

    var sMutableLiveData = MutableLiveData<MutableList<AccountList>>()

    fun getVideoRepository() {
        launch({
            videoRepository.getVideoRepository()
        }, {
            sMutableLiveData.postValue(it.data)
        }, {
            cancelLoadMore()
            cancelRefresh()
        }, messages = *arrayOf("请求中...", "请求成功...", "请求失败..."))
    }

}


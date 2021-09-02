package com.yang.module_video.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.module_video.model.AccountList
import com.yang.module_video.model.VideoData
import com.yang.module_video.model.VideoDataItem
import com.yang.module_video.model.VideoTypeData
import com.yang.module_video.repository.VideoRepository
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

    var mVideoData = MutableLiveData<VideoData>()

    var mVideoItemData = MutableLiveData<MutableList<VideoDataItem>>()

    var mVideoTypeData = MutableLiveData<MutableList<VideoTypeData>>()

    fun getVideoRepository() {
        launch({
            videoRepository.getVideoRepository()
        }, {
            sMutableLiveData.postValue(it.data)
        }, {
            cancelRefreshLoadMore()
        },errorDialog = false)
    }


    fun getVideoInfo(type:String,pageNum:Int) {
        launch({
            videoRepository.getVideoInfo(type,pageNum)
        }, {
            mVideoData.postValue(it.data)
        },{
            cancelRefreshLoadMore()
        },errorDialog = false)
    }
    fun getVideoItemData(sid:String) {
        launch({
            videoRepository.getVideoItemData(sid)
        }, {
            mVideoItemData.postValue(it.data)
        },errorDialog = false)
    }
    fun getVideoTypeData() {
        launch({
            videoRepository.getVideoTypeData()
        }, {
            mVideoTypeData.postValue(it.data)
        },{
            val mutableListOf = mutableListOf<VideoTypeData>()
            for (i in 1..10){
                mutableListOf.add(
                    VideoTypeData(
                        1,
                        "推荐",
                        "1",
                        ""
                    )
                )
            }
            mVideoTypeData.postValue(mutableListOf)
        }, "加载中...")
    }

}


package com.example.module_video.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.lib_common.base.viewmodel.BaseViewModel
import com.example.module_video.model.AccountList
import com.example.module_video.model.VideoData
import com.example.module_video.model.VideoDataItem
import com.example.module_video.model.VideoTypeData
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

    var mVideoData = MutableLiveData<VideoData>()

    var mVideoItemData = MutableLiveData<MutableList<VideoDataItem>>()

    var mVideoTypeData = MutableLiveData<MutableList<VideoTypeData>>()

    fun getVideoRepository() {
        launch({
            videoRepository.getVideoRepository()
        }, {
            sMutableLiveData.postValue(it.data)
        }, {
            cancelLoadMore()
            cancelRefresh()
        })
    }


    fun getVideoInfo(type:String,pageNum:Int) {
        launch({
            videoRepository.getVideoInfo(type,pageNum)
        }, {
            mVideoData.postValue(it.data)
        },{
            cancelLoadMore()
            cancelRefresh()
        })
    }
    fun getVideoItemData(sid:String) {
        launch({
            videoRepository.getVideoItemData(sid)
        }, {
            mVideoItemData.postValue(it.data)
        })
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
        })
    }

}


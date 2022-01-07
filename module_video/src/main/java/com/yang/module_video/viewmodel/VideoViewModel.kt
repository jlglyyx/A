package com.yang.module_video.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.VideoData
import com.yang.lib_common.room.entity.VideoDataItem
import com.yang.lib_common.room.entity.VideoTypeData
import com.yang.module_video.R
import com.yang.module_video.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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


    var mVideoData = MutableLiveData<VideoData>()

    var mVideoItemData = MutableLiveData<MutableList<VideoDataItem>>()

    var mVideoTypeData = MutableLiveData<MutableList<VideoTypeData>>()

    fun getVideoInfo(
        type: String = "",
        pageNum: Int,
        keyword: String = "",
        showDialog: Boolean = false
    ) {
        if (showDialog) {
            launch({
                val mutableMapOf = mutableMapOf<String, Any>()
                mutableMapOf[AppConstant.Constant.TYPE] = type
                mutableMapOf[AppConstant.Constant.KEYWORD] = keyword
                mutableMapOf[AppConstant.Constant.PAGE_NUMBER] = pageNum
                mutableMapOf[AppConstant.Constant.PAGE_SIZE] = AppConstant.Constant.PAGE_SIZE_COUNT
                videoRepository.getVideoInfo(mutableMapOf)
            }, {
                mVideoData.postValue(it.data)
            },{
                cancelRefreshLoadMore()
                showRecyclerViewErrorEvent()
            }, messages = *arrayOf(getString(R.string.string_loading)))
        } else {
            launch({
                val mutableMapOf = mutableMapOf<String, Any>()
                if (!TextUtils.isEmpty(type)) {
                    mutableMapOf[AppConstant.Constant.TYPE] = type
                }
                if (!TextUtils.isEmpty(keyword)) {
                    mutableMapOf[AppConstant.Constant.KEYWORD] = keyword
                }
                mutableMapOf[AppConstant.Constant.PAGE_NUMBER] = pageNum
                mutableMapOf[AppConstant.Constant.PAGE_SIZE] = AppConstant.Constant.PAGE_SIZE_COUNT
                videoRepository.getVideoInfo(mutableMapOf)
            }, {
                mVideoData.postValue(it.data)
            },{
                cancelRefreshLoadMore()
                showRecyclerViewErrorEvent()
            }, errorDialog = false)
        }

    }

    fun getVideoItemData(sid: String) {
        launch({
            videoRepository.getVideoItemData(sid)
        }, {
            mVideoItemData.postValue(it.data)
        }, {
            withContext(Dispatchers.IO) {
                if (BaseAppDatabase.instance.videoDataDao().queryData().size != 0) {
                    mVideoItemData.postValue(
                        BaseAppDatabase.instance.videoDataDao().queryDataBySid(sid)
                    )
                }
            }
        }, errorDialog = false)
    }


    fun getVideoTypeData() {
        launch({
            videoRepository.getVideoTypeData()
        }, {
            mVideoTypeData.postValue(it.data)
        }, {
            requestFail()
        }, messages = *arrayOf(getString(R.string.string_loading)))
    }


    fun insertViewHistory(id: String, type: String) {
        launch({
            videoRepository.insertViewHistory(id, type)
        }, {

        })
    }

    fun insertComment(params: Map<String, String>) {
        launch({
            videoRepository.insertComment(params)
        }, {

        })
    }
}


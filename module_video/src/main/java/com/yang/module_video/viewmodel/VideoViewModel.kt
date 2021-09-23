package com.yang.module_video.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.VideoTypeData
import com.yang.module_video.model.AccountList
import com.yang.module_video.model.VideoData
import com.yang.module_video.model.VideoDataItem
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
    fun getVideoInfo(type:String = "",pageNum:Int,keyword:String = "",showDialog:Boolean = false) {
        if (showDialog){
            launch({
                val mutableMapOf = mutableMapOf<String, Any>()
                mutableMapOf[AppConstant.Constant.TYPE] = type
                mutableMapOf[AppConstant.Constant.PAGE_NUMBER] = pageNum
                mutableMapOf[AppConstant.Constant.PAGE_SIZE] = AppConstant.Constant.PAGE_SIZE_COUNT
                mutableMapOf[AppConstant.Constant.KEYWORD] = keyword
                videoRepository.getVideoInfo(mutableMapOf)
            }, {
                mVideoData.postValue(it.data)
            },{
                showRecyclerViewErrorEvent()
                cancelRefreshLoadMore()
            }, messages = *arrayOf("加载中"))
        }else{
            launch({
                val mutableMapOf = mutableMapOf<String, Any>()
                mutableMapOf[AppConstant.Constant.TYPE] = type
                mutableMapOf[AppConstant.Constant.PAGE_NUMBER] = pageNum
                mutableMapOf[AppConstant.Constant.PAGE_SIZE] = AppConstant.Constant.PAGE_SIZE_COUNT
                mutableMapOf[AppConstant.Constant.KEYWORD] = keyword
                videoRepository.getVideoInfo(mutableMapOf)
            }, {
                mVideoData.postValue(it.data)
            },{
                showRecyclerViewErrorEvent()
                cancelRefreshLoadMore()
            },errorDialog = false)
        }

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
            withContext(Dispatchers.IO){
                mVideoTypeData.postValue(BaseAppDatabase.instance.videoTypeDao().queryData())
            }
        }, "加载中...")
    }

}


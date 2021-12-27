package com.yang.module_video.viewmodel

import android.app.Application
import android.os.Environment
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.VideoData
import com.yang.lib_common.room.entity.VideoDataItem
import com.yang.lib_common.room.entity.VideoTypeData
import com.yang.lib_common.util.getDirectoryName
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

    fun getVideoInfo(type: String = "", pageNum: Int, keyword: String = "", showDialog: Boolean = false) {
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
            }, {
                showRecyclerViewErrorEvent()
                cancelRefreshLoadMore()
                withContext(Dispatchers.IO) {
                    if (BaseAppDatabase.instance.videoDataDao().queryData().size == 0) {
                        val mutableListOf = mutableListOf<VideoDataItem>()
                        val directoryName = getDirectoryName("${Environment.getExternalStorageDirectory()}/MFiles/video/B")
                        var count = 0
                        directoryName.forEachIndexed { index, file ->
                            val listFiles = file.listFiles()
                            listFiles?.forEachIndexed { childIndex, childFile ->
                                mutableListOf.add(VideoDataItem().apply {
                                    id = "${count++}"
                                    videoTitle = childFile.name
                                    videoUrl = childFile.absolutePath
                                    videoType = "$index"
                                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                                        add(VideoDataItem().apply {
                                            id = "${count++}"
                                            videoTitle = childFile.name
                                            videoUrl = childFile.absolutePath
                                            videoType = "$index"
                                        })
                                        add(VideoDataItem().apply {
                                            id = "${count++}"
                                            videoTitle = childFile.name
                                            videoUrl = childFile.absolutePath
                                            videoType = "$index"
                                        })
                                        add(VideoDataItem().apply {
                                            id = "${count++}"
                                            videoTitle = childFile.name
                                            videoUrl = childFile.absolutePath
                                            videoType = "$index"
                                        })
                                    }
                                })
                            }
                        }
                        BaseAppDatabase.instance.videoDataDao().insertData(mutableListOf)
                        mVideoData.postValue(VideoData(BaseAppDatabase.instance.videoDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }else{
                        mVideoData.postValue(VideoData(BaseAppDatabase.instance.videoDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }
                }
            }, messages = *arrayOf("加载中"))
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
            }, {
//                showRecyclerViewErrorEvent()
//                cancelRefreshLoadMore()

                withContext(Dispatchers.IO) {
                    if (BaseAppDatabase.instance.videoDataDao().queryData().size == 0) {
                        val mutableListOf = mutableListOf<VideoDataItem>()
                        val directoryName = getDirectoryName("${Environment.getExternalStorageDirectory()}/MFiles/video/B")
                        var count = 0
                        directoryName.forEachIndexed { index, file ->
                            val listFiles = file.listFiles()
                            listFiles?.forEachIndexed { childIndex, childFile ->
                                mutableListOf.add(VideoDataItem().apply {
                                    id = "${count++}"
                                    videoTitle = childFile.name
                                    videoUrl = childFile.absolutePath
                                    videoType = "$index"
                                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                                        add(VideoDataItem().apply {
                                            id = "${count++}"
                                            videoTitle = childFile.name
                                            videoUrl = childFile.absolutePath
                                            videoType = "$index"
                                        })
                                        add(VideoDataItem().apply {
                                            id = "${count++}"
                                            videoTitle = childFile.name
                                            videoUrl = childFile.absolutePath
                                            videoType = "$index"
                                        })
                                        add(VideoDataItem().apply {
                                            id = "${count++}"
                                            videoTitle = childFile.name
                                            videoUrl = childFile.absolutePath
                                            videoType = "$index"
                                        })
                                    }
                                })
                            }
                        }
                        BaseAppDatabase.instance.videoDataDao().insertData(mutableListOf)
                        mVideoData.postValue(VideoData(BaseAppDatabase.instance.videoDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }else{
                        mVideoData.postValue(VideoData(BaseAppDatabase.instance.videoDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }
                }
            }, errorDialog = false)
        }

    }

    fun getVideoItemData(sid: String) {
        launch({
            videoRepository.getVideoItemData(sid)
        }, {
            mVideoItemData.postValue(it.data)
        },{
            withContext(Dispatchers.IO) {
                if (BaseAppDatabase.instance.videoDataDao().queryData().size != 0) {
                    mVideoItemData.postValue(BaseAppDatabase.instance.videoDataDao().queryDataBySid(sid))
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
            withContext(Dispatchers.IO) {
                if (BaseAppDatabase.instance.videoTypeDao().queryData().size == 0) {
                    val mutableListOf = mutableListOf<VideoTypeData>()
                    val directoryName = getDirectoryName("${Environment.getExternalStorageDirectory()}/MFiles/video/B")
                    directoryName.forEachIndexed { index, s ->
                        mutableListOf.add(
                            VideoTypeData(
                                index,
                                s.name,
                                "$index",
                                ""
                            )
                        )
                    }
                    BaseAppDatabase.instance.videoTypeDao().insertData(mutableListOf)
                    mVideoTypeData.postValue(BaseAppDatabase.instance.videoTypeDao().queryData())
                }else{
                    mVideoTypeData.postValue(BaseAppDatabase.instance.videoTypeDao().queryData())
                }

            }
        }, "加载中...")
    }


    fun addViewHistory(id: String, type: String) {
        launch({
            videoRepository.addViewHistory(id, type)
        }, {

        })
    }

    fun addComment(params: Map<String, String>) {
        launch({
            videoRepository.addComment(params)
        }, {

        })
    }
}


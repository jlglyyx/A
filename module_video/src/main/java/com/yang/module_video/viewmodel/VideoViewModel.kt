package com.yang.module_video.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.VideoTypeData
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
                val mutableListOf = mutableListOf<VideoDataItem>()
                mutableListOf.add(VideoDataItem())
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl = "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202011%2F17%2F20201117105437_45d41.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640489453&t=5685c55865958fe47ba34bbfe0b91405"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                    }
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl =
                        "https://scpic2.chinaz.net/Files/pic/pic9/202107/bpic23656_s.jpg"
                    isLarge = true
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                            isLarge = false
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                            isLarge = false
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                            isLarge = false
                        })
                    }
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl =
                        "https://scpic1.chinaz.net/Files/pic/pic9/202107/apic33909_s.jpg"
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                    }
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl = "https://scpic.chinaz.net/Files/pic/pic9/202107/bpic23678_s.jpg"
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl = "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4166_s.jpg"
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                    }
                })
                mVideoData.postValue(VideoData(mutableListOf, null, null, null, null))
                Log.i("TAG", "initView====: ${GsonBuilder().create().toJson(mutableListOf)}")
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
                showRecyclerViewErrorEvent()
                cancelRefreshLoadMore()
                val mutableListOf = mutableListOf<VideoDataItem>()
                mutableListOf.add(VideoDataItem().apply {
                    videoUrl = "https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg"
                    videoType = "这是类型"
                    videoTitle = "这是标题"
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202011%2F17%2F20201117105437_45d41.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640489453&t=5685c55865958fe47ba34bbfe0b91405"
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic2.chinaz.net/Files/pic/pic9/202107/bpic23656_s.jpg"
                        })
                        videoType = "这是类型"
                    }
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl =
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202011%2F17%2F20201117105437_45d41.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640489453&t=5685c55865958fe47ba34bbfe0b91405"
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202011%2F17%2F20201117105437_45d41.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640489453&t=5685c55865958fe47ba34bbfe0b91405"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                    }
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl =
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202011%2F17%2F20201117105437_45d41.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640489453&t=5685c55865958fe47ba34bbfe0b91405"
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                    }
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl = "https://scpic.chinaz.net/Files/pic/pic9/202107/bpic23678_s.jpg"
                })
                mutableListOf.add(VideoDataItem().apply {
                    videoTitle = "这是标题"
                    videoUrl = "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4166_s.jpg"
                    smartVideoUrls = mutableListOf<VideoDataItem>().apply {
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                        add(VideoDataItem().apply {
                            videoTitle = "这是标题"
                            videoUrl =
                                "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
                        })
                    }
                })
                mVideoData.postValue(VideoData(mutableListOf, null, null, null, null))
                Log.i("TAG", "initView====: ${GsonBuilder().create().toJson(mutableListOf)}")
            }, errorDialog = false)
        }

    }

    fun getVideoItemData(sid: String) {
        launch({
            videoRepository.getVideoItemData(sid)
        }, {
            mVideoItemData.postValue(it.data)
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
                    for (i in 1..10) {
                        mutableListOf.add(
                            VideoTypeData(
                                i,
                                "推=${i}=荐",
                                "1",
                                ""
                            )
                        )
                    }
                    mVideoTypeData.postValue(mutableListOf)
                } else {
                    mVideoTypeData.postValue(BaseAppDatabase.instance.videoTypeDao().queryData())
                }
            }
        }, "加载中...")
    }

}


package com.yang.module_picture.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.ImageData
import com.yang.lib_common.room.entity.ImageDataItem
import com.yang.lib_common.room.entity.ImageTypeData
import com.yang.lib_common.util.getDirectoryName
import com.yang.module_picture.repository.PictureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    var mImageData = MutableLiveData<ImageData>()

    var mImageItemData = MutableLiveData<MutableList<ImageDataItem>>()

    var mImageTypeData = MutableLiveData<MutableList<ImageTypeData>>()

    fun getImageInfo(type:String = "",pageNum:Int,keyword:String = "",showDialog:Boolean = false) {
        if (showDialog){
            launch({
                val mutableMapOf = mutableMapOf<String, Any>()
                if (!TextUtils.isEmpty(type)){
                    mutableMapOf[AppConstant.Constant.TYPE] = type
                }
                if (!TextUtils.isEmpty(keyword)){
                    mutableMapOf[AppConstant.Constant.KEYWORD] = keyword
                }
                mutableMapOf[AppConstant.Constant.PAGE_NUMBER] = pageNum
                mutableMapOf[AppConstant.Constant.PAGE_SIZE] = AppConstant.Constant.PAGE_SIZE_COUNT
                pictureRepository.getImageInfo(mutableMapOf)
            }, {
                mImageData.postValue(it.data)
            },{
                showRecyclerViewErrorEvent()
                cancelRefreshLoadMore()
                withContext(Dispatchers.IO) {
                    if (BaseAppDatabase.instance.imageDataDao().queryData().size == 0) {
                        val directoryName = getDirectoryName()
                        val mutableListOf = mutableListOf<ImageDataItem>()
                        var count = 0
                        directoryName.forEachIndexed { index, file ->
                            val listFiles = file.listFiles()
                            listFiles.forEachIndexed { chilldIndex, chilldFile ->
                                mutableListOf.add(
                                    ImageDataItem(
                                        System.currentTimeMillis().toString(),
                                        "${count++}",
                                        chilldFile.name,
                                        "$index",
                                        chilldFile.absolutePath,
                                        chilldFile.name,
                                        chilldFile.name,
                                        "",
                                        System.currentTimeMillis().toString()
                                    )
                                )
                            }
                        }
                        BaseAppDatabase.instance.imageDataDao().insertData(mutableListOf)
                        mImageData.postValue(ImageData(BaseAppDatabase.instance.imageDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }else{
                        mImageData.postValue(ImageData(BaseAppDatabase.instance.imageDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }

                }
            }, messages = *arrayOf("加载中"))
        }else{
            launch({
                val mutableMapOf = mutableMapOf<String, Any>()
                if (!TextUtils.isEmpty(type)){
                    mutableMapOf[AppConstant.Constant.TYPE] = type
                }
                if (!TextUtils.isEmpty(keyword)){
                    mutableMapOf[AppConstant.Constant.KEYWORD] = keyword
                }
                mutableMapOf[AppConstant.Constant.PAGE_NUMBER] = pageNum
                mutableMapOf[AppConstant.Constant.PAGE_SIZE] = AppConstant.Constant.PAGE_SIZE_COUNT
                pictureRepository.getImageInfo(mutableMapOf)
            }, {
                mImageData.postValue(it.data)
            },{
//                showRecyclerViewErrorEvent()
//                cancelRefreshLoadMore()
                withContext(Dispatchers.IO) {
                    if (BaseAppDatabase.instance.imageDataDao().queryData().size == 0) {
                        val directoryName = getDirectoryName()
                        val mutableListOf = mutableListOf<ImageDataItem>()
                        var count = 0
                        directoryName.forEachIndexed { index, file ->
                            val listFiles = file.listFiles()
                            listFiles.forEachIndexed { chilldIndex, chilldFile ->
                                mutableListOf.add(
                                    ImageDataItem(
                                        System.currentTimeMillis().toString(),
                                        "${count++}",
                                        chilldFile.name,
                                        "$index",
                                        chilldFile.absolutePath,
                                        chilldFile.name,
                                        chilldFile.name,
                                        "",
                                        System.currentTimeMillis().toString()
                                    )
                                )
                            }
                        }
                        BaseAppDatabase.instance.imageDataDao().insertData(mutableListOf)
                        mImageData.postValue(ImageData(BaseAppDatabase.instance.imageDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }else{
                        mImageData.postValue(ImageData(BaseAppDatabase.instance.imageDataDao().queryDataByType(type,pageNum,AppConstant.Constant.PAGE_SIZE_COUNT),null,null,null,null))
                    }

                }
            },errorDialog = false)
        }

    }
    fun getImageItemData(sid:String) {
        launch({
            pictureRepository.getImageItemData(sid)
        }, {
            mImageItemData.postValue(it.data)
        },errorDialog = false)
    }
    fun getImageTypeData() {
        launch({
            pictureRepository.getImageTypeData()
        }, {
            mImageTypeData.postValue(it.data)
        }, {
            withContext(Dispatchers.IO) {
                if (BaseAppDatabase.instance.imageTypeDao().queryData().size == 0) {
                    val mutableListOf = mutableListOf<ImageTypeData>()
                    val directoryName = getDirectoryName()
                    directoryName.forEachIndexed { index, s ->
                        mutableListOf.add(
                            ImageTypeData(
                                index,
                                s.name,
                                "$index",
                                ""
                            )
                        )
                    }
                    BaseAppDatabase.instance.imageTypeDao().insertData(mutableListOf)
                    mImageTypeData.postValue(BaseAppDatabase.instance.imageTypeDao().queryData())
                }else{
                    mImageTypeData.postValue(BaseAppDatabase.instance.imageTypeDao().queryData())
                }

            }
        }, "加载中...")
    }


    fun addViewHistory(id: String, type: String) {
        launch({
            pictureRepository.addViewHistory(id, type)
        }, {

        })
    }

    fun addComment(params: Map<String, String>) {
        launch({
            pictureRepository.addComment(params)
        }, {

        })
    }
}


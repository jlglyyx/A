package com.yang.module_picture.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.entity.ImageData
import com.yang.lib_common.room.entity.ImageDataItem
import com.yang.lib_common.room.entity.ImageTypeData
import com.yang.module_picture.R
import com.yang.module_picture.repository.PictureRepository
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

    fun getImageInfo(
        type: String = "",
        pageNum: Int,
        keyword: String = "",
        showDialog: Boolean = false
    ) {
        if (showDialog) {
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
                pictureRepository.getImageInfo(mutableMapOf)
            }, {
                mImageData.postValue(it.data)
            }, {
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
                pictureRepository.getImageInfo(mutableMapOf)
            }, {
                mImageData.postValue(it.data)
            }, {
                cancelRefreshLoadMore()
                showRecyclerViewErrorEvent()
            }, errorDialog = false)
        }

    }

    fun getImageItemData(sid: String) {
        launch({
            pictureRepository.getImageItemData(sid)
        }, {
            mImageItemData.postValue(it.data)
        }, errorDialog = false)
    }

    fun getImageTypeData() {
        launch({
            pictureRepository.getImageTypeData()
        }, {
            mImageTypeData.postValue(it.data)
        }, {
            requestFail()
        }, messages = *arrayOf(getString(R.string.string_loading)))
    }


    fun addViewHistory(id: String, type: String) {
        launch({
            pictureRepository.addViewHistory(id, type)
        }, {

        })
    }

    fun insertComment(params: Map<String, String>) {
        launch({
            pictureRepository.insertComment(params)
        }, {

        })
    }
}


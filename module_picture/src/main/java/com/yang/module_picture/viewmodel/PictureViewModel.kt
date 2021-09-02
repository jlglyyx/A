package com.yang.module_picture.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.room.entity.ImageTypeData
import com.yang.module_picture.model.ImageData
import com.yang.module_picture.model.ImageDataItem
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

    fun getImageInfo(type:String,pageNum:Int) {
        launch({
            pictureRepository.getImageInfo(type,pageNum)
        }, {
            mImageData.postValue(it.data)
        },{
            cancelRefreshLoadMore()
        },errorDialog = false)
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
        },{
            val mutableListOf = mutableListOf<ImageTypeData>()
            for (i in 1..10){
                mutableListOf.add(
                    ImageTypeData(
                        1,
                        "推荐",
                        "1",
                        ""
                    )
                )
            }
            mImageTypeData.postValue(mutableListOf)
        }, "加载中...")
    }

}


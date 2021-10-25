package com.yang.module_picture.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.yang.lib_common.base.viewmodel.BaseViewModel
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.room.BaseAppDatabase
import com.yang.lib_common.room.entity.ImageTypeData
import com.yang.module_picture.model.ImageData
import com.yang.module_picture.model.ImageDataItem
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
                val mutableListOf = mutableListOf<ImageDataItem>()
                for (i in 0..9){
                    mutableListOf.add(ImageDataItem(null,null,"","","https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg","","","",""))
                }
                mImageData.postValue(ImageData(mutableListOf,null,null,null,null))
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
                showRecyclerViewErrorEvent()
                cancelRefreshLoadMore()
                val mutableListOf = mutableListOf<ImageDataItem>()
                for (i in 0..9){
                    mutableListOf.add(ImageDataItem(null,null,"","","https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg","","","",""))
                }
                mImageData.postValue(ImageData(mutableListOf,null,null,null,null))
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
                    for (i in 1..10) {
                        mutableListOf.add(
                            ImageTypeData(
                                i,
                                "推=${i}=荐",
                                "1",
                                ""
                            )
                        )
                    }
                    mImageTypeData.postValue(mutableListOf)
                }else{
                    mImageTypeData.postValue(BaseAppDatabase.instance.imageTypeDao().queryData())
                }

            }
        }, "加载中...")
    }

}


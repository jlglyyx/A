package com.yang.lib_common.util

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.yang.lib_common.app.BaseApplication

/**
 * @Author Administrator
 * @ClassName LocationUtil
 * @Description
 * @Date 2021/8/20 11:40
 */
class LocationUtil : LifecycleObserver {

    private val TAG = "LocationUtil"
    private lateinit var aMapLocationClientOption:AMapLocationClientOption
    private lateinit var aMapLocationClient: AMapLocationClient
    private lateinit var mLocationListener : AMapLocationListener

    fun startLocation(){
        aMapLocationClientOption = AMapLocationClientOption()
        aMapLocationClient = AMapLocationClient(BaseApplication.baseApplication)

        //回调监听
        mLocationListener = AMapLocationListener {

            if (it.errorCode == 0){

                Log.i(TAG, "startLocation: ${it.toString()}  ${it.toStr()}")
            }else{
                Log.e(TAG,"location Error, ErrCode:${it.errorCode }, errInfo:${it.errorInfo}")
            }

        }
        aMapLocationClient.setLocationListener(mLocationListener)

        //场景模式
        aMapLocationClientOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        aMapLocationClient.stopLocation()
        aMapLocationClient.startLocation()

        //定位模式

        if (NetworkUtil.isNetworkClient(BaseApplication.baseApplication)){
            aMapLocationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        }else{
            aMapLocationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Device_Sensors
        }

        aMapLocationClientOption.isNeedAddress = true
        aMapLocationClientOption.isMockEnable = true
        aMapLocationClientOption.httpTimeOut = 20000
        aMapLocationClientOption.isLocationCacheEnable = false
        aMapLocationClient.setLocationOption(aMapLocationClientOption)
        aMapLocationClient.startLocation()
    }


    fun stopLocation(){
        aMapLocationClient.stopLocation()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        aMapLocationClient.onDestroy()
    }
}
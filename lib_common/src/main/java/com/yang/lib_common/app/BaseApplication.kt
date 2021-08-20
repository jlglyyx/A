package com.yang.lib_common.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.yang.lib_common.BuildConfig
import com.yang.lib_common.handle.CrashHandle
import com.yang.lib_common.helper.getRemoteComponent
import com.yang.lib_common.util.NetworkUtil
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        baseApplication = this
        getRemoteComponent()
        initCrashReport(baseApplication)
        initARouter(baseApplication)
        initGlide(baseApplication)
        initMMKV(baseApplication)
        initNetworkStatusListener(baseApplication)
        initVideo()
    }

    companion object {
        lateinit var baseApplication: BaseApplication
    }

    private fun initARouter(application: BaseApplication) {
        GlobalScope.launch(Dispatchers.Unconfined) {
            if (BuildConfig.DEBUG) {
                ARouter.openLog()
                ARouter.openDebug()
            }
            ARouter.init(application)
        }
    }

    private fun initCrashReport(application: BaseApplication) {
        GlobalScope.launch(Dispatchers.IO) {
            delay(3000)
            CrashReport.initCrashReport(application, "4f807733a2", BuildConfig.DEBUG)
            createNotificationChannel()
        }
        Thread.setDefaultUncaughtExceptionHandler(CrashHandle.instance)
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("download", "下载通知", NotificationManager.IMPORTANCE_HIGH)
            val systemService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            systemService.createNotificationChannel(notificationChannel)
        }
    }

    private fun initMMKV(application: BaseApplication) {
        GlobalScope.launch(Dispatchers.IO) {
            val initialize = MMKV.initialize(application)
            Log.i("TAG", "initMMKV: $initialize")
        }
    }

    private fun initGlide(application: BaseApplication) {
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            Glide.get(application)
        }
    }
    private fun initNetworkStatusListener(application: BaseApplication) {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(),NetworkUtil.getNetworkStatus())
    }

    private fun initVideo(){
        GlobalScope.launch(Dispatchers.IO) {
            delay(3000)
            PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        }

    }
}
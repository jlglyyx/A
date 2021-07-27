package com.example.lib_common.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.lib_common.BuildConfig
import com.example.lib_common.help.getRemoteComponent
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            Glide.get(applicationContext)
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("download", "下载通知", NotificationManager.IMPORTANCE_HIGH)
            val systemService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            systemService.createNotificationChannel(notificationChannel)
        }
    }
}
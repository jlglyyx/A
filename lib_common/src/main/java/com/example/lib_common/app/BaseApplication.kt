package com.example.lib_common.app

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.lib_common.BuildConfig
import com.example.lib_common.help.getRemoteComponent
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


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
        initMMKV()
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
            CrashReport.initCrashReport(application, "4f807733a2", BuildConfig.DEBUG)
        }
    }

    private fun initMMKV() {
        GlobalScope.launch(Dispatchers.IO) {
            val initialize = MMKV.initialize(this@BaseApplication)
            Log.i("TAG", "initMMKV: $initialize")
        }
    }

    private fun initGlide(application: BaseApplication) {
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            Glide.get(application)
        }
    }
}
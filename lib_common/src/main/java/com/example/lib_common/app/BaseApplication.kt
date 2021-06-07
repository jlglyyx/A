package com.example.lib_common.app

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.base.di.component.DaggerBaseComponent
import com.example.lib_common.base.di.module.BaseModule
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        baseApplication = this
        DaggerBaseComponent.builder().baseModule(BaseModule(baseApplication)).build()
        initCrashReport(baseApplication)
        initARouter(baseApplication)
    }

    companion object {
        lateinit var baseApplication: BaseApplication
    }

    private fun initARouter(application: BaseApplication) {
        GlobalScope.launch(Dispatchers.Unconfined) {
            if (BuildConfig.DEBUG) {
                ARouter.openLog();
                ARouter.openDebug();
            }
            ARouter.init(application);
        }
    }
    private fun initCrashReport(application: BaseApplication) {
        GlobalScope.launch(Dispatchers.IO) {
            CrashReport.initCrashReport(application, "4f807733a2", BuildConfig.DEBUG)
        }
    }
}
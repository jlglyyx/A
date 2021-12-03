package com.yang.lib_common.down

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * @Author Administrator
 * @ClassName DownLoadManagerService
 * @Description
 * @Date 2021/12/3 16:22
 */
class DownLoadManagerService:Service() {

    companion object{
        private const val TAG = "DownLoadManagerService"
    }

    private lateinit var downLoadManagerBinder:DownLoadManagerBinder

    override fun onBind(intent: Intent?): IBinder {
        return downLoadManagerBinder
    }


    inner class DownLoadManagerBinder: Binder() ,DownLoadManagerListener{

        fun startDown(builder:DownLoadManager.Builder){
            builder.downLoadManagerListener(this)
            builder.build()
        }

        override fun onSuccess(downloadPath: String, time: Long) {
            Log.i(TAG, "onSuccess: $downloadPath 用时:${time/1000f}秒")
        }

        override fun onFailed(errorMessage: String) {

        }

        override fun onPaused() {

        }

        override fun onCanceled() {

        }

        override fun onProgress(progress: Float) {
            Log.i(TAG, "onProgress: ${progress * 100}%")
        }

        override fun onChildProgress(name: String, progress: Float) {
            Log.i(TAG, "onChildProgress $name 下载: ${progress * 100}%")
        }
    }

    override fun onCreate() {
        super.onCreate()
        downLoadManagerBinder = DownLoadManagerBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
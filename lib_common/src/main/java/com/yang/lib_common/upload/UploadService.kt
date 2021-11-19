package com.yang.lib_common.upload

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * @Author Administrator
 * @ClassName UploadService
 * @Description
 * @Date 2021/11/19 9:25
 */
class UploadService:Service() {
    override fun onBind(intent: Intent?): IBinder {
        return UploadServiceBinder()
    }

    inner class UploadServiceBinder: Binder(){

        private fun startUpload(filePath:String){
            UploadManage.instance.startUpload(filePath)
        }

    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
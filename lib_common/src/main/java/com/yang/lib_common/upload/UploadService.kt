package com.yang.lib_common.upload

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.yang.lib_common.R
import com.yang.lib_common.constant.AppConstant
import okhttp3.Call
import kotlin.random.Random

/**
 * @Author Administrator
 * @ClassName UploadService
 * @Description
 * @Date 2021/11/19 9:25
 */
class UploadService : Service(), UploadListener {

    companion object {
        const val UPLOAD_SERVICE_ID = 100
    }

    override fun onBind(intent: Intent?): IBinder {
        return UploadServiceBinder()
    }

    inner class UploadServiceBinder : Binder() {

        fun startUpload(filePath: MutableList<String>):Call {
            return UploadManage.instance.startUpload(filePath)
        }

        fun cancelUpload(uploadCall: Call){
            UploadManage.instance.cancelUpload(uploadCall)
        }
    }

    override fun onProgress(noticeId: Int, progress: Int) {

        showProgressNotice(noticeId, progress)
    }

    override fun onSuccess(noticeId: Int) {
        showCompleteNotice(noticeId)
    }

    override fun onFailed(noticeId: Int) {

    }

    override fun onCreate() {
        super.onCreate()
        UploadManage.instance.addUploadListener(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showProgressNotice(noticeId: Int, progress: Int) {
        Log.i("TAG", "onProcess: $progress $noticeId")
        val systemService = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val build = NotificationCompat.Builder(this, AppConstant.NoticeChannel.DOWNLOAD)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setProgress(100, progress, true)
            .setContentTitle("当前进度:$progress")
            .setAutoCancel(true)
            .build()
        systemService.notify(noticeId, build)
    }

    private fun showCompleteNotice(noticeId: Int) {
        val systemService = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        systemService.cancel(noticeId)
        val build = NotificationCompat.Builder(this, AppConstant.NoticeChannel.DOWNLOAD)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("上传完成")
            .setAutoCancel(true)
            .build()
        systemService.notify(Random.nextInt(noticeId, Int.MAX_VALUE), build)
    }


}
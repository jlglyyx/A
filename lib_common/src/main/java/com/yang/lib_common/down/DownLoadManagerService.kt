package com.yang.lib_common.down

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.yang.lib_common.R
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter

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
            stopForeground(true)
        }

        override fun onFailed(errorMessage: String) {

        }

        override fun onPaused() {

        }

        override fun onCanceled() {

        }

        override fun onProgress(progress: Float) {
            Log.i(TAG, "onProgress: ${progress * 100}%")
            startForeground(123,showDownLoadNotification((progress * 100).toInt(),0,0))
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
        stopForeground(true)
    }



    /**
     * 下载进度通知
     */
    private fun showDownLoadNotification(progress: Int, usedTimeMillis: Int, downloadSpeed: Int): Notification {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        return  NotificationCompat.Builder(this, "download")
            .setContentIntent(PendingIntent.getActivity(this,123,
                Intent(this, buildARouter(AppConstant.RoutePath.MAIN_ACTIVITY)::class.java),PendingIntent.FLAG_UPDATE_CURRENT))
            .setContentTitle("下载速度：$downloadSpeed Kb/s $progress% 用时：$usedTimeMillis/s")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setProgress(100, progress, true)
            .setAutoCancel(true)
            .build()
        //notificationManager.notify(1, build)
    }
}
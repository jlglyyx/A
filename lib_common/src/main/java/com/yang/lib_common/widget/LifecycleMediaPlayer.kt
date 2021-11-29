package com.yang.lib_common.widget

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.yang.lib_common.observer.ILifecycleObserver

/**
 * @Author Administrator
 * @ClassName LifecycleMediaPlayer
 * @Description
 * @Date 2021/11/29 15:00
 */
class LifecycleMediaPlayer : SurfaceView, SurfaceHolder.Callback, ILifecycleObserver {

    companion object {
        private const val TAG = "LifecycleMediaPlayer"
    }

    private var mediaPlayer: MediaPlayer? = null

    private var mHolder: SurfaceHolder = holder

    private var first = true

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mHolder.addCallback(this@LifecycleMediaPlayer)
        mediaPlayer = MediaPlayer()
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        mediaPlayer?.apply {
            setAudioAttributes(
                AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )
            mediaPlayer?.setDataSource("/storage/emulated/0/MFiles/video/register.mp4")
            isLooping = true
            setOnPreparedListener {
                start()
            }
            prepareAsync()
        }

    }

    private fun stop(){
        mediaPlayer?.let {
            if (it.isPlaying){
                it.stop()
            }
        }
    }


    private var isSurfaceCreated = false

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mediaPlayer?.setDisplay(holder)
        isSurfaceCreated = true
        mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!)
        onResume()
        Log.i(TAG, "surfaceCreated: ")
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        mediaPlayer?.setDisplay(holder)
        Log.i(TAG, "surfaceChanged: ")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        isSurfaceCreated = false
        Log.i(TAG, "surfaceDestroyed: ")
    }


    override fun onResume() {
        mediaPlayer?.let {
            if (!it.isPlaying){
                it.start()
            }
        }
    }

    override fun onPause() {
        mediaPlayer?.let {
            if (it.isPlaying){
                it.pause()
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.let {
            if (it.isPlaying){
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }
}
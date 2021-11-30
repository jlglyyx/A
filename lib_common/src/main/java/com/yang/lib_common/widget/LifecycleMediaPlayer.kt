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

    var mediaPlayer: MediaPlayer? = null

    private var mHolder: SurfaceHolder = holder

    private var isSurfaceCreated = true

    var position = 0

    private var isReset = false

    private var mWidth = 0f

    private var mHeight = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mHolder.addCallback(this@LifecycleMediaPlayer)
        mediaPlayer = MediaPlayer()
    }

    fun initMediaPlayer(path: String): MediaPlayer? {
        return mediaPlayer?.apply {
            setAudioAttributes(
                AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )
            mediaPlayer?.setDataSource(path)
            isLooping = true
            setOnPreparedListener {
                start()
                if (isReset) {
                    mediaPlayer?.seekTo(position)
                    Log.i(TAG, "setOnPreparedListener: $position")
                    isReset = false
                }
                val videoWidth = it.videoWidth
                val videoHeight = it.videoHeight
                Log.i(TAG, "onMeasure===: ${mediaPlayer?.videoWidth} ${mediaPlayer?.videoHeight}  $mWidth $mHeight")
                if (videoHeight <= mHeight/5*4){
                    setMeasuredDimension(mWidth.toInt(), (videoHeight * mWidth /videoWidth).toInt())
                }
            }
            setOnInfoListener { mp, what, extra ->
                Log.i(TAG, "setOnInfoListener: $mp   $what   $extra")
                return@setOnInfoListener false
            }
            setOnErrorListener { mp, what, extra ->
                Log.i(TAG, "setOnErrorListener: $mp   $what   $extra")
                if (what == -38) {
                    mp.reset()
                    isReset = true
                    initMediaPlayer(path)
                }
                return@setOnErrorListener true
            }
            prepareAsync()
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        canvas.save()
//        canvas.drawColor((0xcc1c093e).toInt())
//        canvas.restore()
//    }


    private fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
        }
    }


    override fun surfaceCreated(holder: SurfaceHolder?) {
        if (!isSurfaceCreated) {
            mediaPlayer?.start()
        }
        mediaPlayer?.setDisplay(holder)
        isSurfaceCreated = true
        Log.i(TAG, "surfaceCreated: $position")
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        isSurfaceCreated = false
        mediaPlayer?.let {
            position = it.currentPosition
            it.pause()
            Log.i(TAG, "surfaceDestroyed: $position")
        }
    }


    override fun onResume() {
//        mediaPlayer?.let {
//            if (!it.isPlaying) {
//                it.start()
//            }
//        }
    }

    override fun onPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }

}
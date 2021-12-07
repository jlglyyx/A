package com.yang.lib_common.widget

import android.content.Context
import android.graphics.Canvas
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.yang.lib_common.observer.ILifecycleObserver
import com.yang.lib_common.util.getScreenPx

/**
 * @Author Administrator
 * @ClassName LifecycleMediaPlayer
 * @Description
 * @Date 2021/11/29 15:00
 */
class LifecycleMediaPlayer : ViewGroup, SurfaceHolder.Callback, ILifecycleObserver {

    companion object {
        private const val TAG = "LifecycleMediaPlayer"
    }

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var mHolder: SurfaceHolder

    private var isSurfaceCreated = true

    private var position = 0

    private var isReset = false

    private var mWidth = 0f

    private var mHeight = 0f

    private var mVideoWidth = 0

    private var mVideoHeight = 0

    private var mContext:Context

    private var screenPx : IntArray

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context!!
        mediaPlayer = MediaPlayer()
        screenPx = getScreenPx(mContext)
        initSurfaceView()
    }


    private fun initSurfaceView(){
        val surfaceView = SurfaceView(mContext)
        mHolder = surfaceView.holder
        mHolder.addCallback(this@LifecycleMediaPlayer)
        this.addView(surfaceView)
    }

    fun initMediaPlayer(path: String): MediaPlayer? {
        try {
            return mediaPlayer?.apply {
                setAudioAttributes(AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build())
                mediaPlayer?.setDataSource(path)
                isLooping = true
                setOnPreparedListener {
                    start()
                    if (isReset) {
                        mediaPlayer?.seekTo(position)
                        Log.i(TAG, "setOnPreparedListener: $position")
                        isReset = false
                    }
                    mVideoWidth = it.videoWidth
                    mVideoHeight = it.videoHeight
                    val childAt = getChildAt(0)
                    if (mVideoHeight <= screenPx[1]/5*3){
                        childAt.layoutParams = LayoutParams(screenPx[0], (mVideoHeight * screenPx[0] /mVideoWidth))
                    }
                    //requestLayout()
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
        }catch (e:Exception){
            return null
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childAt = getChildAt(0)
        if(screenPx[1] == childAt.measuredHeight){
            childAt.layout(0,0, screenPx[0], screenPx[1])
        }else{
            childAt.layout(0, (screenPx[1]/2 - childAt.measuredHeight/2), childAt.measuredWidth, screenPx[1]/2 + childAt.measuredHeight/2)
        }
        Log.i(TAG, "onLayout: ${childAt.measuredWidth}  ${childAt.measuredHeight}")
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        Log.i(TAG, "onMeasure: $measuredWidth  $measuredHeight")
        val childAt = getChildAt(0)
        Log.i(TAG, "onMeasuresssss: ${childAt.measuredWidth}  ${childAt.measuredHeight}")
    }


    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        //canvas.drawColor(Color.GREEN)
    }

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
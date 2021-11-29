package com.yang.lib_common.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import com.yang.lib_common.observer.ILifecycleObserver

/**
 * @Author Administrator
 * @ClassName LifecycleVideoView
 * @Description
 * @Date 2021/11/29 10:51
 */
class LifecycleVideoView : VideoView, ILifecycleObserver {

    companion object {
        private const val TAG = "LifecycleVideoView"
    }

    var mMediaController: MediaController? = null

    private var resumePosition = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mMediaController = MediaController(context)
        //setMediaController(mMediaController)

        setOnCompletionListener {
            start()
        }

        setOnErrorListener { mp, what, extra ->
            Log.i(TAG, "setOnErrorListener: $mp   $what   $extra")
            return@setOnErrorListener true
        }

        setOnPreparedListener {
            it.setOnSeekCompleteListener {
                if (!isPlaying) {
                    start()
                }
            }
        }
    }

    override fun onResume() {
        if (!isPlaying) {
            seekTo(resumePosition)
            Log.i("TAG", "onResume: $resumePosition   $currentPosition   $duration")
        }
    }

    override fun onPause() {
        if (isPlaying) {
            pause()
            resumePosition = currentPosition
            Log.i("TAG", "onPause: $resumePosition   $currentPosition   $duration")
        }
    }

    override fun onDestroy() {
        stopPlayback()
        resumePosition = 0
        Log.i("TAG", "stopPlayback: ")
    }
}
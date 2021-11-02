package com.yang.lib_common.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.yang.lib_common.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ClassName: ImageScrollView.
 * Created by Administrator on 2021/4/1_9:56.
 * Describe:
 */
class ImageScrollView : FrameLayout, LifecycleObserver {

    companion object {
        private const val TAG = "ImageScrollView"
    }

    private var bitmap: Bitmap
    private lateinit var scaleBitmap: Bitmap
    private var mPaint = Paint()
    private var mMatrix = Matrix()
    private var mBitmapCount = 0
    private var mPanDistance = 0f
    private var mJob: Job? = null

    private var w: Int = 0
    private var h: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setWillNotDraw(false)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.iv_login_bg)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w
        this.h = h
        init(bitmap)
    }

    private fun init(bitmap:Bitmap){
        val copy = bitmap.copy(Bitmap.Config.RGB_565, true)
        scaleBitmap = scaleBitmap(copy, w, h)
        mBitmapCount = measuredHeight / scaleBitmap.height + 1
        if (!copy.isRecycled) {
            copy.recycle()
            System.gc()
        }
    }
    
    fun setBitMap(bitmap:Bitmap){
        init(bitmap)
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val height = scaleBitmap.height
        if (height + mPanDistance != 0f) {
            mMatrix.reset()
            mMatrix.postTranslate(0f, mPanDistance)
            canvas.drawBitmap(scaleBitmap, mMatrix, mPaint)
        }
        if (height + mPanDistance < measuredHeight) {
            for (i in 0 until mBitmapCount) {
                mMatrix.reset()
                mMatrix.postTranslate(0f, (i + 1) * scaleBitmap.height + mPanDistance)
                canvas.drawBitmap(scaleBitmap, mMatrix, mPaint)
            }
        }
        invalidateView()

    }


    private fun invalidateView() {
        mJob = GlobalScope.launch(Dispatchers.IO) {
            var length = scaleBitmap.height
            if (length + mPanDistance <= 0f) {
                mPanDistance = 0f
            }
            mPanDistance -= 0.5f
            postInvalidate()
        }
    }

    private fun scaleBitmap(bitmap: Bitmap, w: Int, h: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        var newHeight: Int
        var newWidth: Int = measuredWidth
        newHeight = newWidth * height / width
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (mJob != null) {
            if (!mJob?.isActive!!) {
                mJob?.start()
            }
        }

        Log.i(TAG, "onResume: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (mJob != null) {
            if (mJob?.isActive!!) {
                mJob?.cancel()
            }
        }
        Log.i(TAG, "onPause: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (mJob != null) {
            if (mJob?.isActive!!) {
                mJob?.cancel()
            }
        }
        mJob = null
        Log.i(TAG, "onDestroy: ")
    }


}
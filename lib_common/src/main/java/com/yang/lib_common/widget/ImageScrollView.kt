package com.yang.lib_common.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yang.lib_common.util.getScreenPx
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

    private lateinit var bitmap: Bitmap
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
//        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ImageScrollView)
//        val string = obtainStyledAttributes.getString(R.styleable.ImageScrollView_imageName)
//        val file = File("${Environment.getExternalStorageDirectory()}/MFiles/picture/${string}.png")
//        if (file.exists()){
//            bitmap = BitmapFactory.decodeFile("${Environment.getExternalStorageDirectory()}/MFiles/picture/${string}.png")
//        }else{
//            bitmap = Bitmap.createBitmap(getScreenPx(context)[0], getScreenPx(context)[1], Bitmap.Config.RGB_565)
//            val canvas = Canvas(bitmap)
//            canvas.drawColor(Color.WHITE)
//        }
//        obtainStyledAttributes.recycle()

        Glide.with(this).asBitmap()
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic2.zhimg.com%2Fv2-583a86cd154739160d2e17e185dcc8f2_r.jpg%3Fsource%3D1940ef5c&refer=http%3A%2F%2Fpic2.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1638427892&t=e2a584b32bb0b6f820613078d552716c")
            .into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        Log.i(TAG, "onResourceReady: $resource")
                        bitmap = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        bitmap = Bitmap.createBitmap(
                            getScreenPx(context)[0],
                            getScreenPx(context)[1],
                            Bitmap.Config.RGB_565
                        )
                        val canvas = Canvas(bitmap)
                        canvas.drawColor(Color.WHITE)
                    }

                })
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w
        this.h = h
        init(bitmap)
    }

    private fun init(bitmap: Bitmap) {
        val copy = bitmap.copy(Bitmap.Config.RGB_565, true)
        scaleBitmap = scaleBitmap(copy, w, h)
        mBitmapCount = measuredHeight / scaleBitmap.height + 1
        if (!copy.isRecycled) {
            copy.recycle()
            System.gc()
        }

        mPaint.colorFilter = ColorMatrixColorFilter(
            floatArrayOf(
                2f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
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
            val length = scaleBitmap.height
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
        val newHeight: Int
        val newWidth: Int = measuredWidth
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
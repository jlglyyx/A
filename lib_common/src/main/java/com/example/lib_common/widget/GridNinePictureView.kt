package com.example.lib_common.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.lib_common.util.dip2px
import kotlin.math.abs
import kotlin.math.ceil

class GridNinePictureView : LinearLayout {

    private var mContext: Context
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var dataSize = 0
    private var spanCount = 3
    private var mImageViewWidth: Int = 0
    private var rectF: RectF = RectF()
    private var maxChild = 9
    private var mPaint = Paint()
    private var mTextPaint = Paint()
    var imageCallback: ImageCallback? = null

    interface ImageCallback {
        fun imageClickListener(position: Int)
    }

    var data: MutableList<String> = mutableListOf()
        set(value) {
            this.removeAllViews()
            field = value
            dataSize = field.size
            if (!field.isNullOrEmpty()) {
                for (i in 0 until if (dataSize > maxChild) {
                    maxChild
                } else {
                    dataSize
                }) {
                    val imageView = ImageView(mContext)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageView.setOnClickListener {
                        imageCallback?.imageClickListener(i)
                    }
                    Glide.with(mContext)
                        .load(
                            data[i]
                        )
                        .into(imageView)
                    addView(imageView)
                }
            }
        }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContext = context
        init()
    }


    private fun init() {

        mPaint.color = Color.parseColor("#afffffff")
        mPaint.style = Paint.Style.FILL

        mTextPaint.color = Color.parseColor("#8a8a8a")
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = 50f.dip2px(mContext).toFloat()

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        layoutImage()
    }

    private fun layoutImage() {
        var countWidth = 0
        var countHeight = 0
        mImageViewWidth = measuredWidth / spanCount
        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            if (i % spanCount == 0) {
                countWidth = 0
                if (i != 0) {
                    countHeight += mImageViewWidth
                }
                childAt.layout(
                    countWidth,
                    countHeight,
                    countWidth + mImageViewWidth,
                    countHeight + mImageViewWidth
                )
            } else {
                countWidth += mImageViewWidth
                childAt.layout(
                    countWidth,
                    countHeight,
                    countWidth + mImageViewWidth,
                    countHeight + mImageViewWidth
                )
                if (i == 8) {
                    rectF.set(
                        countWidth.toFloat(),
                        countHeight.toFloat(),
                        (countWidth + mImageViewWidth).toFloat(),
                        (countHeight + mImageViewWidth).toFloat()
                    )
                }
            }

        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(
            widthSize,
            (measuredWidth / spanCount) * ceil(
                if (dataSize > maxChild) {
                    maxChild.toFloat()
                } else {
                    dataSize.toFloat()
                } / spanCount.toFloat()
            ).toInt()
        )

    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (dataSize - maxChild > 0) {
            val exceedCount = dataSize - maxChild
            val bgLayer = canvas.saveLayer(rectF, null)
            canvas.drawRect(rectF, mPaint)
            canvas.restoreToCount(bgLayer)
            val textLayer = canvas.saveLayer(rectF, null)
            val drawTextContent = "+$exceedCount"
            canvas.drawText(
                drawTextContent,
                (rectF.left + (rectF.right - rectF.left) / 2) - mTextPaint.measureText(
                    drawTextContent
                ) / 2,
                (rectF.top + (rectF.bottom - rectF.top) / 2) + (abs(
                    mTextPaint.ascent() + abs(
                        mTextPaint.descent()
                    )
                )) / 2,
                mTextPaint
            )
            canvas.restoreToCount(textLayer)
        }
    }


}
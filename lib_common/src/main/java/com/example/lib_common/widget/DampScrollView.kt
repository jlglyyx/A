package com.example.lib_common.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView

class DampScrollView : NestedScrollView {

    /**scrollView的第一个子view*/
    lateinit var parentView: View

    /**记录scrollView的第一个子view位置*/
    private val rect = Rect()

    /**手指按下起始位置*/
    private var startY = 0

    /**偏移比率*/
    private var damping = 0.5f

    /**动画执行时间*/
    private var resetDuration = 300

    private var mContext: Context


    companion object {
        private const val TAG = "DampScrollView"
    }

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        this.mContext = context
        init(attrs!!)
    }



    private fun init(attrs: AttributeSet){
        //val obtainStyledAttributes = mContext.obtainStyledAttributes(attrs, R.styleable.DampScrollView)
        //damping = obtainStyledAttributes.getFloat(R.styleable.DampScrollView_damping,damping)
       // resetDuration = obtainStyledAttributes.getInt(R.styleable.DampScrollView_resetDuration,resetDuration)
        //obtainStyledAttributes.recycle()
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            parentView = getChildAt(0)
        }else{
            throw Exception("item is null")
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = ev.y.toInt()
                rect.set(parentView.left, parentView.top, parentView.right, parentView.bottom)
            }
            MotionEvent.ACTION_MOVE -> {
                val offset = ev.y - startY
                if ((isBottom() && offset < 0) || (isTop() && offset > 0)) {
                    with(rect) {
                        parentView.layout(
                            left,
                            top + (offset * damping).toInt(),
                            right,
                            bottom + (offset * damping).toInt()
                        )
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                resetAnimation()
                parentView.layout(rect.left, rect.top, rect.right, rect.bottom)
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {

        return super.onTouchEvent(ev)
    }


    /**
     * 判断scrollView 是否滑动到顶部
     */
    private fun isTop(): Boolean {
        return scrollY == 0
    }

    /**
     * 判断scrollView 是否滑动到底部
     */
    private fun isBottom(): Boolean {
        return parentView.measuredHeight <= height + scrollY
    }

    /**
     * 回弹动画
     */
    private fun resetAnimation() {
        var translateAnimation =
            TranslateAnimation(0f, 0f, parentView.top.toFloat(), rect.top.toFloat())
        translateAnimation.duration = resetDuration.toLong()
        translateAnimation.interpolator = AccelerateInterpolator()
        parentView.animation = translateAnimation
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawColor(ContextCompat.getColor(mContext,R.color.holo_purple))
    }

}
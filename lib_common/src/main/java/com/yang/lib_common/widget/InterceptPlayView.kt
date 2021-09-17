package com.yang.lib_common.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @Author Administrator
 * @ClassName InterceptPlayView
 * @Description
 * @Date 2021/9/17 17:22
 */
class InterceptPlayView: ConstraintLayout {

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i("TAG", "onInterceptTouchEvent: ")
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("TAG", "onTouchEvent: ")
        return super.onTouchEvent(event)
    }
}
package com.yang.lib_common.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.yang.lib_common.R

/**
 * @Author Administrator
 * @ClassName ATabLayout
 * @Description
 * @Date 2022/1/6 15:58
 */
class ATabLayout : FrameLayout {

    private var a = true

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        //clipChildren = false

        val textView = TextView(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = bottom
            text = "测试"
        }
        addView(textView)
        val imageView = ImageView(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setImageResource(R.drawable.iv_bear)
        }
        addView(imageView)
        textView.visibility = View.GONE
        imageView.visibility = View.VISIBLE
        val textViewAnimator = ObjectAnimator.ofFloat(textView, "rotationX", 90f, 0f)
        textViewAnimator.duration = 1000

        val imageViewAnimator = ObjectAnimator.ofFloat(imageView, "rotationX", 0f, -90f)
        imageViewAnimator.duration = 1000



        textViewAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (a) {
                    textView.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    imageViewAnimator.start()
                    a = true
                }

            }
        })
        imageViewAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                //if (a) {
                    imageView.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textViewAnimator.start()
                    a = false
                //}

            }
        })


        setOnClickListener {
            if (a) {
                imageViewAnimator.start()
            } else {
                textViewAnimator.start()
            }
        }


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }


}
package com.example.lib_common.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lib_common.R
import com.example.lib_common.util.getStatusBarHeight

class CommonToolBar : ConstraintLayout {

    var imageBackCallBack: ImageBackCallBack? = null

    var imageAddCallBack: ImageAddCallBack? = null

    var tVRightCallBack: TVRightCallBack? = null

    interface ImageBackCallBack {
        fun imageBackClickListener(view: View)
    }

    interface ImageAddCallBack {
        fun imageAddClickListener(view: View)
    }

    interface TVRightCallBack {
        fun tvRightClickListener(view: View)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs!!)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val inflate = LayoutInflater.from(context).inflate(R.layout.view_common_toolbar, this)
        val clToolbar = inflate.findViewById<ConstraintLayout>(R.id.cl_toolbar)
        clToolbar.setPadding(0, getStatusBarHeight(context), 0, 0)
        val tvCenterContent = inflate.findViewById<TextView>(R.id.tv_centerContent)
        val tvLeftContent = inflate.findViewById<TextView>(R.id.tv_leftContent)
        val tvRightContent = inflate.findViewById<TextView>(R.id.tv_rightContent)
        val ivBack = inflate.findViewById<ImageView>(R.id.iv_back)
        val ivAdd = inflate.findViewById<ImageView>(R.id.iv_add)
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.CommonToolBar)
        val centerContent =
            obtainStyledAttributes.getString(R.styleable.CommonToolBar_centerContent)
        val leftContent = obtainStyledAttributes.getString(R.styleable.CommonToolBar_leftContent)
        val leftImgVisible = obtainStyledAttributes.getBoolean(R.styleable.CommonToolBar_leftImgVisible,true)

        val rightContent = obtainStyledAttributes.getString(R.styleable.CommonToolBar_rightContent)
        val rightImgVisible =
            obtainStyledAttributes.getBoolean(R.styleable.CommonToolBar_rightImgVisible, false)
        val rightContentVisible =
            obtainStyledAttributes.getBoolean(R.styleable.CommonToolBar_rightContentVisible, false)

        val toolbarBg = obtainStyledAttributes.getResourceId(
            R.styleable.CommonToolBar_toolbarBg, 0)
        if (toolbarBg != 0){
            clToolbar.setBackgroundResource(toolbarBg)
        }
        tvCenterContent.text = centerContent
        tvLeftContent.text = leftContent
        tvRightContent.text = rightContent
        if (leftImgVisible) {
            ivBack.visibility = View.VISIBLE
        } else {
            ivBack.visibility = View.GONE
        }
        if (rightImgVisible) {
            ivAdd.visibility = View.VISIBLE
        } else {
            ivAdd.visibility = View.GONE
        }
        if (rightContentVisible) {
            tvRightContent.visibility = View.VISIBLE
        } else {
            tvRightContent.visibility = View.GONE
        }
        ivAdd.setOnClickListener {
            imageAddCallBack?.imageAddClickListener(it)
        }
        ivBack.setOnClickListener {
            if (null != imageBackCallBack) {
                imageBackCallBack?.imageBackClickListener(it)
            } else {
                (context as Activity).finish()
            }
        }
        tvRightContent.setOnClickListener {
            tVRightCallBack?.tvRightClickListener(it)
        }
        obtainStyledAttributes.recycle()
    }

}
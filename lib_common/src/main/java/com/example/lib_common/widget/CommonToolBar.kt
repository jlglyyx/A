package com.example.lib_common.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lib_common.R

class CommonToolBar : ConstraintLayout {

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
        val tvTitle = inflate.findViewById<TextView>(R.id.tv_title)
        val ivBack = inflate.findViewById<ImageView>(R.id.iv_back)
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.CommonToolBar)
        val title = obtainStyledAttributes.getString(R.styleable.CommonToolBar_title)
        tvTitle.text = title
        ivBack.setOnClickListener {
            (context as Activity).finish()
        }
        obtainStyledAttributes.recycle()
    }

}
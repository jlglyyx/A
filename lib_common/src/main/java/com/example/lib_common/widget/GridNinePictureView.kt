package com.example.lib_common.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide

class GridNinePictureView : FrameLayout {

    private var mContext: Context;
    private var mWidth: Int = 0;
    private var mHeight: Int = 0;
    private var dataSize = 7
    private var spanCount = 3
    private var mImageViewWidth: Int = 0

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
        for (i in 0 until dataSize) {
            val imageView = ImageView(mContext)
            imageView.layoutParams = ViewGroup.LayoutParams(mImageViewWidth, mImageViewWidth)
            Glide.with(mContext).load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg").into(imageView)
            addView(imageView)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mImageViewWidth = mWidth / spanCount
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        layoutImage()
    }

    private fun layoutImage() {
        var countWidth = 0
        var countHeight = 0
        var columnCount = 0
        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            if (i % spanCount == 0) {
                countWidth = 0
                columnCount++
                countHeight = columnCount* mImageViewWidth
                childAt.layout(countWidth, countHeight, mImageViewWidth, countHeight+mImageViewWidth)
            } else {
                childAt.layout(countWidth, countHeight, countWidth+mImageViewWidth, countHeight+mImageViewWidth)
            }
            countWidth += mImageViewWidth

        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

//    private fun init(){
//        val inflate = LayoutInflater.from(mContext).inflate(R.layout.view_grid_nine_picture, this)
//        val recyclerView = inflate.findViewById<RecyclerView>(R.id.recyclerView)
//        val gridLayoutManager = GridLayoutManager(mContext, 3)
//        recyclerView.layoutManager = gridLayoutManager
//        recyclerView.adapter = GridNinePictureAdapter(mutableListOf<String>().apply {
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//            add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1336119765,2231343437&fm=26&gp=0.jpg")
//        }, R.layout.view_item_grid_nine_picture)
//    }
//
//    inner class GridNinePictureAdapter(data:MutableList<String>, layoutResId: Int) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
//
//        override fun convert(helper: BaseViewHolder, item: String) {
//            val ivImage = helper.getView<ImageView>(R.id.iv_nine_image)
//            Glide.with(mContext).load(item).into(ivImage)
//        }
//
//    }

}
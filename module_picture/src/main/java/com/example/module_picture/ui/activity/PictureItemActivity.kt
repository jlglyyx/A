package com.example.module_picture.ui.activity

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.lib_common.util.buildARouter
import com.example.module_picture.R
import com.example.module_picture.di.factory.PictureViewModelFactory
import com.example.module_picture.helper.getPictureComponent
import com.example.module_picture.model.ImageDataItem
import com.example.module_picture.viewmodel.PictureViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.act_picture_item.*
import kotlinx.android.synthetic.main.fra_item_picture.recyclerView
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max


@Route(path = AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
class PictureItemActivity : BaseActivity() {

    private lateinit var mAdapter: MAdapter
    private lateinit var mCommentAdapter: MCommentAdapter
    private val MILLISECONDS_PER_INCH = 0.03f

    @Inject
    lateinit var pictureViewModelFactory: PictureViewModelFactory

    private lateinit var pictureModule: PictureViewModel

    override fun getLayout(): Int {
        return R.layout.act_picture_item
    }

    override fun initData() {
        val intent = intent
        val sid = intent.getStringExtra(AppConstant.Constant.ID)
        pictureModule.getImageItemData(sid ?: "")
    }

    override fun initView() {
        initRecyclerView()
        initGalleryRecyclerView()
    }

    override fun initViewModel() {
        getPictureComponent().inject(this)
        pictureModule = getViewModel(pictureViewModelFactory, PictureViewModel::class.java)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return pictureModule.uC
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MAdapter(R.layout.item_image, mutableListOf()).also {
            it.setOnItemClickListener { adapter, view, position ->
                val data = adapter.data
                val images = mutableListOf<String>().apply {
                    data.forEach { imageData ->
                        imageData as ImageDataItem
                        add(imageData.imageUrl)
                    }
                }
                val imageViewPagerDialog =
                    ImageViewPagerDialog(this, images, position)
                imageViewPagerDialog.imageViewPagerDialogCallBack = object :
                    ImageViewPagerDialog.ImageViewPagerDialogCallBack {
                    override fun getPosition(position: Int) {
                        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                            position,
                            0
                        )
                    }

                }
                XPopup.Builder(this).asCustom(imageViewPagerDialog).show()

            }
        }
        mCommentAdapter =
            MCommentAdapter(R.layout.item_picture_comment, mutableListOf<String>().apply {
                this.add("今天天气很好啊")
                this.add("好看好看")
                this.add("这技术绝了")
                this.add("哈哈哈哈哈")
            }).also {
                it.setOnItemChildClickListener { adapter, view, position ->
                    when (view.id) {
                        R.id.siv_img -> {
                            buildARouter(
                                AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY
                            ).navigation()
                        }
                    }
                }
            }
        val concatAdapter = ConcatAdapter(mAdapter, mCommentAdapter)
        recyclerView.adapter = concatAdapter
        pictureModule.mImageItemData.observe(this, Observer {
            mAdapter.replaceData(it)
        })


    }

    var currentPosition = 0
    var mCurrentItemOffset = 0
    private fun initGalleryRecyclerView() {
        galleryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val linearSnapHelper = LinearSnapHelper()

        linearSnapHelper.attachToRecyclerView(galleryRecyclerView)

        val mGalleryAdapter = MGalleryAdapter(R.layout.item_image, mutableListOf<String>().apply {
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
        })
        galleryRecyclerView.adapter = mGalleryAdapter

        galleryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i(TAG, "onScrolled: $dx")
                mCurrentItemOffset += dx
                recyclerView.layoutManager?.let {
                    val view = linearSnapHelper.findSnapView(it)
                    if (view != null) {
                        val position = recyclerView.getChildAdapterPosition(view)
                        currentPosition = position
                        val offset: Int = mCurrentItemOffset - currentPosition * view.width
                        val percent = max(abs(offset) * 1.0 / view.width, 0.0001).toFloat()
                        Log.i(TAG, "percent:#$currentPosition  $percent")
                        var leftView: View? = null
                        var rightView: View? = null
                        if (currentPosition >= 1) {
                            leftView = it.findViewByPosition(currentPosition - 1)
                        }
                        if (currentPosition <= 1) {
                            rightView = it.findViewByPosition(mGalleryAdapter.data.size - 1)
                        }
                        var currentView: View? = it.findViewByPosition(currentPosition)

                        leftView?.scaleY = ((1 - 0.5) * percent + 0.5).toFloat()
                        rightView?.scaleY = ((1 - 0.5) * percent + 0.5).toFloat()
                        currentView?.scaleY = ((0.5 - 1) * percent + 1).toFloat()

                        val rect = Rect()
                        val localVisibleRect = view.getLocalVisibleRect(rect)
                        if (!localVisibleRect) {
                            view.scaleY = 1.0f
                        }

                    }
                }


            }
        })
    }


    inner class MAdapter(layoutResId: Int, list: MutableList<ImageDataItem>) :
        BaseQuickAdapter<ImageDataItem, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: ImageDataItem) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            val avi = helper.getView<AVLoadingIndicatorView>(R.id.avi)
            helper.setTag(R.id.siv_img, item.id)
            Glide.with(sivImg)
                .load(item.imageUrl)
                .into(object : CustomViewTarget<ShapeableImageView, Drawable>(sivImg) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        avi.visibility = View.VISIBLE
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        sivImg.setImageDrawable(null)
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        avi.visibility = View.GONE
                        if (sivImg.tag == item.id) {
                            sivImg.setImageDrawable(resource)
                        }
                    }
                })
        }
    }

    inner class MCommentAdapter(layoutResId: Int, list: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: String) {
            helper.addOnClickListener(R.id.siv_img)
            helper.setText(R.id.tv_comment, item)
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            Glide.with(sivImg)
                .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
                .into(sivImg)
        }
    }

    inner class MGalleryAdapter(layoutResId: Int, list: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: String) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            val avi = helper.getView<AVLoadingIndicatorView>(R.id.avi)
            avi.visibility = View.GONE
            Glide.with(sivImg)
                .load(item)
                .into(sivImg)
        }
    }
}
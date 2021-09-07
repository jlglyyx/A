package com.yang.module_picture.ui.activity

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.dialog.EditBottomDialog
import com.yang.lib_common.dialog.ImageViewPagerDialog
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.module_picture.R
import com.yang.module_picture.helper.getPictureComponent
import com.yang.module_picture.model.ImageDataItem
import com.yang.module_picture.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.act_picture_item.*
import kotlinx.android.synthetic.main.fra_item_picture.recyclerView
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
class PictureItemActivity : BaseActivity() {

    private lateinit var mAdapter: MAdapter
    private lateinit var mCommentAdapter: MCommentAdapter

    @Inject
    lateinit var pictureModule: PictureViewModel

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
        tv_send_comment.clicks().subscribe {
            XPopup.Builder(this).autoOpenSoftInput(true).asCustom(EditBottomDialog(this).apply {
                dialogCallBack = object : EditBottomDialog.DialogCallBack {
                    override fun getComment(s: String) {
                        mCommentAdapter.addData(0, s)
                        recyclerView.smoothScrollToPosition(0)
                    }

                }
            }).show()
        }
    }

    override fun initViewModel() {
        getPictureComponent(this).inject(this)
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
                        imageData.imageUrl?.let { it1 -> add(it1) }
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
                this.add("哈哈哈哈哈")
                this.add("哈哈哈哈哈")
                this.add("哈哈哈哈哈")
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

    private fun initGalleryRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        galleryRecyclerView.layoutManager = linearLayoutManager
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
        }).also {
            it.setOnItemClickListener { adapter, view, position ->
                val data = adapter.data
                val imageViewPagerDialog =
                    ImageViewPagerDialog(this, data as MutableList<String>, position)
                XPopup.Builder(this).asCustom(imageViewPagerDialog).show()

            }
        }
        galleryRecyclerView.adapter = mGalleryAdapter
        var currentPosition = 0
        var mCurrentItemOffset = 0
//        galleryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                //Log.i(TAG, "onScrolled: $dx  $dy  ${recyclerView.getChildAt(0).width}")
//                mCurrentItemOffset += dx
//                val view = linearSnapHelper.findSnapView(linearLayoutManager)
//                if (view != null) {
//                    val position = recyclerView.getChildAdapterPosition(view)
//                    currentPosition = position
//                    Log.i(TAG, "onScrolled: $position   $mCurrentItemOffset")
//                    val offset: Int = mCurrentItemOffset - currentPosition * view.width
//                    val percent = max(abs(offset) * 1.0 / view.width, 0.0001).toFloat()
//                    var leftView: View? = null
//                    var rightView: View? = null
//                    if (currentPosition >= 1) {
//                        leftView = linearLayoutManager.findViewByPosition(currentPosition - 1)
//                    }
//                    if (currentPosition <= 1) {
//                        rightView =
//                            linearLayoutManager.findViewByPosition(mGalleryAdapter.data.size - 1)
//                    }
//                    var currentView: View? = linearLayoutManager.findViewByPosition(currentPosition)
//
//                    leftView?.scaleY = percent
//                    rightView?.scaleY = percent
//                    currentView?.scaleY = percent
////                    currentView?.scaleY = ((0.5 - 1) * percent + 1).toFloat()
//
////                    val rect = Rect()
////                    val localVisibleRect = view.getLocalVisibleRect(rect)
////                    if (!localVisibleRect) {
////                        view.scaleY = 1.0f
////                    }
//
//                }
//
//
//            }
//        })
    }


    inner class MAdapter(layoutResId: Int, list: MutableList<ImageDataItem>) :
        BaseQuickAdapter<ImageDataItem, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: ImageDataItem) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            helper.setTag(R.id.siv_img, item.id)
            Glide.with(sivImg)
                .load(item.imageUrl)
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(sivImg)
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
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(sivImg)
        }
    }

    inner class MGalleryAdapter(layoutResId: Int, list: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: String) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            Glide.with(sivImg)
                .load(item)
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(sivImg)
        }
    }
}
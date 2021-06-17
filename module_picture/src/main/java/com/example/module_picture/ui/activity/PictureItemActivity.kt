package com.example.module_picture.ui.activity

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.example.module_picture.R
import com.example.module_picture.di.factory.PictureViewModelFactory
import com.example.module_picture.helper.getPictureComponent
import com.example.module_picture.model.ImageData
import com.example.module_picture.model.ImageDataItem
import com.example.module_picture.viewmodel.PictureViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.fra_item_picture.*
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
class PictureItemActivity : BaseActivity() {

    lateinit var mAdapter: MAdapter

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
        pictureModule.getImageItemData(sid!!)
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {
        getPictureComponent().inject(this)
        pictureModule = getViewModel(pictureViewModelFactory, PictureViewModel::class.java)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return pictureModule.uC
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager =  LinearLayoutManager(this)
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
        recyclerView.adapter = mAdapter
        pictureModule.mImageItemData.observe(this, Observer {
            mAdapter.replaceData(it)
        })
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<ImageDataItem>) :
        BaseQuickAdapter<ImageDataItem, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: ImageDataItem) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            val avi = helper.getView<AVLoadingIndicatorView>(R.id.avi)
            helper.setTag(R.id.siv_img,item.id)
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
                        if (sivImg.tag == item.id){
                            sivImg.setImageDrawable(resource)
                        }
                    }
                })
        }
    }
}
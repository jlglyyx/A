package com.yang.module_picture.ui.activity

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.yang.module_picture.data.model.CommonData
import com.yang.module_picture.data.model.ImageDataItem
import com.yang.module_picture.helper.getPictureComponent
import com.yang.module_picture.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.act_picture_item.*
import kotlinx.android.synthetic.main.fra_item_picture.recyclerView
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
class PictureItemActivity : BaseActivity() {

    private lateinit var mAdapter: MAdapter
    private lateinit var mCommentAdapter: MCommentAdapter

    @Inject
    lateinit var pictureViewModel: PictureViewModel

    override fun getLayout(): Int {
        return R.layout.act_picture_item
    }

    override fun initData() {
        val intent = intent
        val sid = intent.getStringExtra(AppConstant.Constant.ID)
        pictureViewModel.getImageItemData(sid ?: "")
    }

    override fun initView() {
        initRecyclerView()
        tv_send_comment.clicks().subscribe {
            XPopup.Builder(this).autoOpenSoftInput(true).asCustom(EditBottomDialog(this).apply {
                dialogCallBack = object : EditBottomDialog.DialogCallBack {
                    override fun getComment(s: String) {
                        mCommentAdapter.addData(0, CommonData(s))
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
        return pictureViewModel.uC
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

                    override fun onViewClickListener(view: View) {

                    }

                }
                XPopup.Builder(this).asCustom(imageViewPagerDialog).show()

            }
        }
        mCommentAdapter =
            MCommentAdapter(mutableListOf<CommonData>().apply {
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
                this.add(CommonData("今天天气很好啊"))
            },R.layout.item_picture_comment).also {
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
        pictureViewModel.mImageItemData.observe(this, Observer {
            mAdapter.replaceData(it)
        })
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

    inner class MCommentAdapter(list: MutableList<CommonData>, layoutResId: Int) :
        BaseQuickAdapter<CommonData, BaseViewHolder>(layoutResId,list) {

        override fun convert(helper: BaseViewHolder, item: CommonData) {
            helper.addOnClickListener(R.id.siv_img)
            helper.setText(R.id.tv_comment, item.content)
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            Glide.with(sivImg)
                .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(sivImg)
        }
    }

}
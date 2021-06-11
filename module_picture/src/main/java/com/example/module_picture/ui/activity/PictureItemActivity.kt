package com.example.module_picture.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.module_picture.R
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fra_item_picture.*


@Route(path = AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
class PictureItemActivity : BaseActivity() {

    lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.act_picture_item
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MAdapter(R.layout.item_image, mutableListOf<String>().apply {
            for (i in 1..50) {
                this.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg")
            }
        }).also {
            it.setOnItemClickListener { adapter, view, position ->
                val imageViewPagerDialog =
                    ImageViewPagerDialog(this, adapter.data as MutableList<String>, position)
                imageViewPagerDialog.imageViewPagerDialogCallBack = object :
                    ImageViewPagerDialog.ImageViewPagerDialogCallBack {
                    override fun getPosition(position: Int) {
                        recyclerView.smoothScrollToPosition(position)
                        //recyclerView.scrollToPosition(position)
                    }

                }
                XPopup.Builder(this).asCustom(imageViewPagerDialog).show()

            }
        }
        recyclerView.adapter = mAdapter
//        pictureModule.sMutableLiveData.observe(this, Observer {
//            mAdapter.replaceData(it)
//        })
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: String) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            Glide.with(sivImg)
                .load(item)
                .into(sivImg)
        }
    }
}
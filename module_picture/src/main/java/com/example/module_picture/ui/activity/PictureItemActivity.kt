package com.example.module_picture.ui.activity

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.dialog.ImageViewPagerDialog
import com.example.lib_common.help.buildARouter
import com.example.module_picture.R
import com.google.android.material.imageview.ShapeableImageView
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fra_item_picture.*


@Route(path = AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
class PictureItemActivity : BaseActivity() {

    private lateinit var mAdapter: MAdapter
    private lateinit var mCommentAdapter: MCommentAdapter

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
            this.add("https://img1.baidu.com/it/u=801991820,3567782121&fm=26&fmt=auto&gp=0.jpg")
            this.add("https://img1.baidu.com/it/u=3266479433,1999627905&fm=26&fmt=auto&gp=0.jpg")
            this.add("https://img1.baidu.com/it/u=1369511624,462595697&fm=26&fmt=auto&gp=0.jpg")
            this.add("https://img2.baidu.com/it/u=1722449517,1620238619&fm=26&fmt=auto&gp=0.jpg")
            for (i in 1..5) {
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
                    when(view.id){
                        R.id.siv_img -> {
                            buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).navigation()
                        }
                    }
                }
            }
        val concatAdapter = ConcatAdapter(mAdapter, mCommentAdapter)
        recyclerView.adapter = concatAdapter
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
}
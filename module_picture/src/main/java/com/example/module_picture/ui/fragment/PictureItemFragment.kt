package com.example.module_picture.ui.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.buildARouter
import com.example.module_picture.R
import com.example.module_picture.di.factory.PictureViewModelFactory
import com.example.module_picture.helper.getPictureComponent
import com.example.module_picture.model.AccountList
import com.example.module_picture.viewmodel.PictureViewModel
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.fra_item_picture.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT)
class PictureItemFragment : BaseLazyFragment() {

    @Inject
    lateinit var pictureViewModelFactory: PictureViewModelFactory

    private lateinit var pictureModule: PictureViewModel

    lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.fra_item_picture
    }

    override fun initData() {
        pictureModule.getPictureRepository()
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return pictureModule.uC
    }

    override fun initViewModel() {
        getPictureComponent().inject(this)
        pictureModule = getViewModel(pictureViewModelFactory, PictureViewModel::class.java)

    }


    private fun initRecyclerView() {
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = MAdapter(R.layout.item_picture_image, mutableListOf()).also {
            it.setOnItemClickListener { adapter, view, position ->
                buildARouter(AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY).navigation()
            }
        }
        recyclerView.adapter = mAdapter
        pictureModule.sMutableLiveData.observe(this, Observer {
            mAdapter.replaceData(it)
        })
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<AccountList>) :
        BaseQuickAdapter<AccountList, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: AccountList) {
            helper.setText(R.id.tv_title, item.id.toString())
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            Glide.with(sivImg)
                .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg")
                .into(sivImg)
        }
    }
}
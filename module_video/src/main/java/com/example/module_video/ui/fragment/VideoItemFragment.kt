package com.example.module_video.ui.fragment

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
import com.example.module_video.R
import com.example.module_video.di.factory.VideoViewModelFactory
import com.example.module_video.helper.getVideoComponent
import com.example.module_video.model.AccountList
import com.example.module_video.viewmodel.VideoViewModel
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.fra_item_video.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT)
class VideoItemFragment : BaseLazyFragment() {

    @Inject
    lateinit var videoViewModelFactory: VideoViewModelFactory

    private lateinit var videoModule: VideoViewModel

    lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.fra_item_video
    }

    override fun initData() {
        videoModule.getVideoRepository()
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return videoModule.uC
    }

    override fun initViewModel() {
        getVideoComponent().inject(this)
        videoModule = getViewModel(videoViewModelFactory, VideoViewModel::class.java)

    }


    private fun initRecyclerView() {
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = MAdapter(R.layout.item_picture_image, mutableListOf()).also {
            it.setOnItemClickListener { adapter, view, position ->
                buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY).navigation()
            }
        }
        recyclerView.adapter = mAdapter
        videoModule.sMutableLiveData.observe(this, Observer {
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
package com.example.module_video.ui.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.buildARouter
import com.example.module_video.R
import com.example.module_video.di.factory.VideoViewModelFactory
import com.example.module_video.helper.getVideoComponent
import com.example.module_video.model.VideoData
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
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)




        recyclerView.layoutManager = gridLayoutManager
        mAdapter = MAdapter(mutableListOf()).also {
            it.setOnItemClickListener { adapter, view, position ->
                buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY).navigation()
            }
        }
        recyclerView.adapter = mAdapter


        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val videoData = mAdapter.data[position]
                return when(videoData.itemType)  {
                    AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE -> 2
                    AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE -> 2
                    AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE -> 1
                    else -> 1
                }
            }

        }

        recyclerView.addItemDecoration(object :RecyclerView.ItemDecoration(){

//            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
//                super.getItemOffsets(outRect, itemPosition, parent)
//                val videoData = mAdapter.data[itemPosition]
//                when(videoData.itemType)  {
//                    AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE -> 2
//                    AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE -> 2
//                    AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE -> 1
//                    else -> 1
//                }
//            }
        })


        videoModule.sMutableLiveData.observe(this, Observer {
            val mutableListOf = mutableListOf<VideoData>()
            it.forEach {
                mutableListOf.add(VideoData(AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE))
                mutableListOf.add(VideoData(AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE).apply {
                    bigTitle = it.name
                })
                mutableListOf.add(VideoData(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
                    smartTitle = it.name
                })
                mutableListOf.add(VideoData(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
                    smartTitle = it.name
                })
                mutableListOf.add(VideoData(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
                    smartTitle = it.name
                })
                mutableListOf.add(VideoData(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
                    smartTitle = it.name
                })
            }
            mAdapter.replaceData(mutableListOf)
        })
    }

    inner class MAdapter(list: MutableList<VideoData>) :
        BaseMultiItemQuickAdapter<VideoData, BaseViewHolder>(list) {

        init {
            addItemType(
                AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE,
                R.layout.item_video_recommend_type
            )
            addItemType(
                AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE,
                R.layout.item_video_big_image
            )
            addItemType(
                AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE,
                R.layout.item_video_smart_image
            )
        }

        override fun convert(helper: BaseViewHolder, item: VideoData) {
            when (item.itemType) {
                AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE -> {

                }
                AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE -> {
                    helper.setText(R.id.tv_title, item.bigTitle)
                    val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
                    Glide.with(sivImg)
                        .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg")
                        .into(sivImg)
                }
                AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE -> {
                    helper.setText(R.id.tv_title, item.smartTitle)
                    val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
                    Glide.with(sivImg)
                        .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg")
                        .into(sivImg)

                }

            }
        }


//        override fun convert(helper: BaseViewHolder, item: AccountList) {
//            helper.setText(R.id.tv_title, item.id.toString())
//            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
//            Glide.with(sivImg)
//                .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg")
//                .into(sivImg)
//        }
    }
}
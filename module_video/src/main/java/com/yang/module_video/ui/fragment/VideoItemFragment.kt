package com.yang.module_video.ui.fragment

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.lib_common.adapter.MBannerAdapter
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.BannerBean
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.dip2px
import com.yang.module_video.R
import com.yang.module_video.helper.getVideoComponent
import com.yang.module_video.model.VideoDataItem
import com.yang.module_video.viewmodel.VideoViewModel
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fra_item_video.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT)
class VideoItemFragment : BaseLazyFragment(), OnRefreshLoadMoreListener {

    @Inject
    lateinit var videoModule: VideoViewModel

    @Inject
    lateinit var gson: Gson

    lateinit var mAdapter: MAdapter

    private var queryType: String? = null

    private var pageNum = 1


    override fun getLayout(): Int {
        return R.layout.fra_item_video
    }

    override fun initData() {
        queryType = arguments?.getString(AppConstant.Constant.TYPE)
        smartRefreshLayout.autoRefresh()
        initRecyclerView()
        val mutableListOf = mutableListOf<VideoDataItem>()
        mutableListOf.add(VideoDataItem(AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE))
        mutableListOf.add(VideoDataItem(AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE).apply {
            videoTitle = "1=="
            videoUrl = "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"
        })
        mutableListOf.add(VideoDataItem(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
            videoTitle = "1=="
            videoUrl =
                "https://scpic2.chinaz.net/Files/pic/pic9/202107/bpic23656_s.jpg"
        })
        mutableListOf.add(VideoDataItem(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
            videoTitle = "1=="
            videoUrl =
                "https://scpic1.chinaz.net/Files/pic/pic9/202107/apic33909_s.jpg"
        })
        mutableListOf.add(VideoDataItem(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
            videoTitle = "1=="
            videoUrl = "https://scpic.chinaz.net/Files/pic/pic9/202107/bpic23678_s.jpg"
        })
        mutableListOf.add(VideoDataItem(AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE).apply {
            videoTitle = "1=="
            videoUrl = "https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4166_s.jpg"
        })
        Log.i(TAG, "initView====: ${gson.toJson(mutableListOf)}")
        mAdapter.replaceData(mutableListOf)

    }

    override fun initView() {

        initBanner()
        initSmartRefreshLayout()

    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return videoModule.uC
    }

    override fun initViewModel() {
        getVideoComponent(this).inject(this)

    }


    private fun initSmartRefreshLayout() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(this)
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager
        mAdapter = MAdapter(mutableListOf()).also {
            it.setOnItemClickListener { adapter, view, position ->
                val videoDataItem = adapter.data[position] as VideoDataItem
                when (videoDataItem.itemType) {
                    AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE -> {

                    }
                    AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE -> {
                        buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY).withString(
                            AppConstant.Constant.ID,
                            videoDataItem.id
                        ).navigation()
                    }
                    AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE -> {
                        buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY).navigation()
                    }

                }
            }
        }
        mAdapter.openLoadAnimation()
        recyclerView.adapter = mAdapter

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val videoData = mAdapter.data[position]
                return when (videoData.itemType) {
                    AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE -> 2
                    AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE -> 2
                    AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE -> 1
                    else -> 1
                }
            }

        }

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val itemPosition = parent.getChildLayoutPosition(view)
                val videoData = mAdapter.data[itemPosition]
                when (videoData.itemType) {
                    AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE -> {
                        if (itemPosition % 2 == 0) {
                            outRect.right = 2f.dip2px(requireContext())
                        } else {
                            outRect.left = 2f.dip2px(requireContext())
                        }
                    }
                }
            }

        })

        videoModule.mVideoData.observe(this, Observer {
            when {
                smartRefreshLayout.isRefreshing -> {
                    smartRefreshLayout.finishRefresh()
                    mAdapter.replaceData(it.list)
                }
                smartRefreshLayout.isLoading -> {
                    smartRefreshLayout.finishLoadMore()
                    if (pageNum != 1 && it.list.isEmpty()) {
                        smartRefreshLayout.setNoMoreData(true)
                    } else {
                        smartRefreshLayout.setNoMoreData(false)
                        mAdapter.addData(it.list)
                    }
                }
                else -> {
                    mAdapter.replaceData(it.list)
                }
            }
        })

        registerRefreshAndRecyclerView(smartRefreshLayout,mAdapter)
    }

    private fun initBanner() {
//        banner.setPageTransformer(AlphaPageTransformer())
        //       banner.addPageTransformer(DepthPageTransformer())
//        banner.addPageTransformer(RotateDownPageTransformer())
//        banner.addPageTransformer(RotateUpPageTransformer())
//        banner.addPageTransformer(RotateYTransformer())
//        banner.addPageTransformer(ScaleInTransformer())
        //       banner.addPageTransformer(ZoomOutPageTransformer())
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(MBannerAdapter(mutableListOf<BannerBean>().apply {
                add(BannerBean("https://scpic.chinaz.net/Files/pic/pic9/202107/bpic23678_s.jpg"))
                add(BannerBean("https://scpic1.chinaz.net/Files/pic/pic9/202107/apic33909_s.jpg"))
                add(BannerBean("https://scpic2.chinaz.net/Files/pic/pic9/202107/bpic23656_s.jpg"))
                add(BannerBean("https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32187.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg"))
                add(BannerBean("https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4166_s.jpg"))
            }))
            .indicator = CircleIndicator(requireContext())
    }

    inner class MAdapter(list: MutableList<VideoDataItem>) :
        BaseMultiItemQuickAdapter<VideoDataItem, BaseViewHolder>(list) {

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

        override fun convert(helper: BaseViewHolder, item: VideoDataItem) {
            when (item.itemType) {
                AppConstant.Constant.ITEM_VIDEO_RECOMMEND_TYPE -> {

                    //helper.setText(R.id.tv_type,item.videoTitle)
                }
                AppConstant.Constant.ITEM_VIDEO_BIG_IMAGE -> {
                    helper.setText(R.id.tv_title, item.videoTitle)
                    val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
                    Glide.with(sivImg)
                        .load(item.videoUrl)
                        .error(R.drawable.iv_image_error)
                        .placeholder(R.drawable.iv_image_placeholder)
                        .into(sivImg)
                }
                AppConstant.Constant.ITEM_VIDEO_SMART_IMAGE -> {
                    helper.setText(R.id.tv_title, item.videoTitle)
                    val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
                    Glide.with(sivImg)
                        .load(item.videoUrl)
                        .error(R.drawable.iv_image_error)
                        .placeholder(R.drawable.iv_image_placeholder)
                        .into(sivImg)

                }

            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        videoModule.getVideoInfo(queryType ?: "", pageNum)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        videoModule.getVideoInfo(queryType ?: "", pageNum)
    }
}
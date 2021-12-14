package com.yang.module_video.ui.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.apt_annotation.InjectViewModel
import com.yang.lib_common.adapter.MBannerAdapter
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.BannerBean
import com.yang.lib_common.room.entity.VideoDataItem
import com.yang.lib_common.util.buildARouter
import com.yang.module_video.R
import com.yang.module_video.helper.getVideoComponent
import com.yang.module_video.viewmodel.VideoViewModel
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fra_item_video.*
import javax.inject.Inject

@InjectViewModel(AppConstant.RoutePath.MODULE_MAIN)
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
        val gridLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = gridLayoutManager
        mAdapter = MAdapter(mutableListOf()).also {
            it.setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.item_video_big_image ->{
                        val videoDataItem = adapter.data[position] as VideoDataItem
                        buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY).withString(
                            AppConstant.Constant.ID,
                            videoDataItem.id
                        ).navigation()
                    }
                    R.id.item_video_recommend_type ->{
                        val videoDataItem = adapter.data[position] as VideoDataItem
                        buildARouter(AppConstant.RoutePath.VIDEO_SCREEN_ACTIVITY).withString(
                            AppConstant.Constant.ID,
                            videoDataItem.id
                        ).navigation()
                    }
                }

            }
        }
        recyclerView.adapter = mAdapter


        videoModule.mVideoData.observe(this, Observer
        {
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

        registerRefreshAndRecyclerView(smartRefreshLayout, mAdapter)
    }


    private fun initBanner() {
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
        BaseQuickAdapter<VideoDataItem, BaseViewHolder>(list) {
        init {
            mLayoutResId = R.layout.item_video
        }

        override fun convert(helper: BaseViewHolder, item: VideoDataItem) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
            helper.setText(R.id.tv_title, item.videoTitle)
                .setText(R.id.tv_type,item.videoType)
                .addOnClickListener(R.id.item_video_big_image)
                .addOnClickListener(R.id.item_video_recommend_type)


            item.videoUrl?.let {
                if (it.endsWith(".mp4")){
                    Glide.with(sivImg)
                        .setDefaultRequestOptions(RequestOptions().frame(5000))
                        .load(item.videoUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.iv_image_error)
                        .placeholder(R.drawable.iv_image_placeholder)
                        .override(1080,500)
                        .into(sivImg)
                }else{
                    Glide.with(sivImg)
                        .load(item.videoUrl)
                        .error(R.drawable.iv_image_error)
                        .placeholder(R.drawable.iv_image_placeholder)
                        .into(sivImg)
                }
            }




            initRecyclerView(recyclerView, item.smartVideoUrls ?: mutableListOf())
        }


        private fun initRecyclerView(
            recyclerView: RecyclerView,
            data: MutableList<VideoDataItem>
        ) {
            recyclerView.adapter = MImageAdapter(data).also {
                it.setOnItemClickListener { adapter, view, position ->
                    val videoDataItem = adapter.data[position] as VideoDataItem
                    buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY).withString(
                        AppConstant.Constant.ID,
                        videoDataItem.id
                    ).navigation()
                }
            }
            recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        }
    }

    inner class MImageAdapter(list: MutableList<VideoDataItem>) :
        BaseQuickAdapter<VideoDataItem, BaseViewHolder>(list) {
        init {
            mLayoutResId = R.layout.item_video_smart_image
        }

        override fun convert(helper: BaseViewHolder, item: VideoDataItem) {
            helper.setText(R.id.tv_title, item.videoTitle)
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            item.videoUrl?.let {
                if (it.endsWith(".mp4")){
                    Glide.with(sivImg)
                        .setDefaultRequestOptions(RequestOptions().frame(5000))
                        .load(item.videoUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.iv_image_error)
                        .placeholder(R.drawable.iv_image_placeholder)
                        .override(1080,500)
                        .into(sivImg)
                }else{
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
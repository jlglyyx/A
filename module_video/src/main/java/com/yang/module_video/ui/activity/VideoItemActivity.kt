package com.yang.module_video.ui.activity

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.XPopup
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.dialog.EditBottomDialog
import com.yang.lib_common.down.thread.MultiMoreThreadDownload
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.module_video.R
import com.yang.module_video.helper.getVideoComponent
import com.yang.module_video.model.VideoDataItem
import com.yang.module_video.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.act_video_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY)
class VideoItemActivity : BaseActivity() {

    @Inject
    lateinit var videoModule: VideoViewModel
    private lateinit var orientationUtils: OrientationUtils
    private lateinit var commentAdapter: CommentAdapter

    private lateinit var collectionAdapter: CollectionAdapter

    private var url = ""

    private var isPause = false
    private var isPlay = false

    private var isScroll = false

    override fun getLayout(): Int {
        return R.layout.act_video_item
    }

    override fun initData() {
        val intent = intent
        val sid = intent.getStringExtra(AppConstant.Constant.ID)
        sid?.let {
            videoModule.getVideoItemData(it)
        }

    }

    override fun initView() {

        initRecyclerView()
        initTabLayout()

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, detailPlayer)
        //初始化不打开外部的旋转
        orientationUtils.isEnable = false
        detailPlayer.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()
            detailPlayer.startWindowFullscreen(this, true, true)
        }

        detailPlayer.backButton.setOnClickListener {
            finish()
        }

        tv_send_comment.clicks().subscribe {
            XPopup.Builder(this).autoOpenSoftInput(true).asCustom(EditBottomDialog(this).apply {
                dialogCallBack = object : EditBottomDialog.DialogCallBack {
                    override fun getComment(s: String) {
                        commentAdapter.addData(0, s)
                        nestedScrollView.fullScroll(View.FOCUS_UP)
                    }

                }
            }).show()
        }
        tv_video_down.clicks().subscribe {
            MultiMoreThreadDownload.Builder(this)
                .parentFilePath("${Environment.getExternalStorageDirectory()}/MFiles/video")
                .filePath("${System.currentTimeMillis()}.mp4")
                .fileUrl(url)
                .threadNum(50)
                .build()
                .start()
        }


    }

    override fun initViewModel() {
        getVideoComponent(this).inject(this)
        videoModule.mVideoItemData.observe(this, Observer {
            for ((index,videoDataItem) in it.withIndex()){
                if (index == 0){
                    videoDataItem.select = true
                }
                videoDataItem.position = index+1
            }
            url = it[0].videoUrl.toString()
            initVideo()
            collectionAdapter.replaceData(it)
        })
    }

    private fun initRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        commentAdapter = CommentAdapter(R.layout.item_video_comment, mutableListOf<String>().apply {
            GlobalScope.launch(Dispatchers.IO) {
                for (i in 1..20) {
                    add(
                        "这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看" +
                                "这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看这电影真好看"
                    )
                }
            }
        }).apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.siv_img -> {
                        buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).withString(AppConstant.Constant.ID,"").navigation()
                    }
                }

            }
        }
        recyclerView.adapter = commentAdapter

        collectionRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        collectionAdapter =
            CollectionAdapter(R.layout.item_video_collection, mutableListOf()).apply {
                setOnItemClickListener { adapter, view, position ->
                    val item = adapter.getItem(position) as VideoDataItem
                    url = item.videoUrl.toString()
                    if (item.select){
                        item.select = false
                        adapter.notifyItemChanged(position)
                    }else{
                        adapter.data.forEach {
                            if ((it as VideoDataItem).select){
                                it.select = false
                            }
                        }
                        item.select = true
                        adapter.notifyDataSetChanged()
                    }
                    initVideo()
                    detailPlayer.startPlayLogic()

                }
            }
        collectionRecyclerView.adapter = collectionAdapter
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val rect = Rect()
            cl_video.getHitRect(rect)
            isScroll = true
            if (cl_video.getLocalVisibleRect(rect)) {
                tabLayout.getTabAt(0)?.select()
            } else {
                tabLayout.getTabAt(1)?.select()
            }
        }


    }

    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().apply {
            view.setOnClickListener {
                isScroll = false
            }
        }.setText("视频"))
        tabLayout.addTab(tabLayout.newTab().apply {
            view.setOnClickListener {
                isScroll = false
            }
        }.setText("评论"))


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (!isScroll) {
                    if (tab?.position == 0) {
                        nestedScrollView.fullScroll(View.FOCUS_UP)
                    } else {
                        cl_video.post {
                            nestedScrollView.scrollY = cl_video.height
                        }
                    }
                }

            }

        })
    }


    private fun initVideo() {

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption
//        gsyVideoOption.setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(url)
            .setCacheWithPlay(false)
            .setVideoTitle("测试视频")
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(
                    url: String,
                    vararg objects: Any
                ) {
                    super.onQuitFullscreen(url, *objects)
                    orientationUtils.backToProtVideo()
                }
            }).setLockClickListener { view, lock ->
                orientationUtils.isEnable = !lock
            }.build(detailPlayer)


    }


    inner class CollectionAdapter(layoutResId: Int, data: MutableList<VideoDataItem>) :
        BaseQuickAdapter<VideoDataItem, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: VideoDataItem) {
            helper.setText(R.id.bt_collection,item.position.toString())
            if (item.select){
                helper.setTextColor(R.id.bt_collection,
                    ContextCompat.getColor(mContext,R.color.mediumslateblue))
            }else{
                helper.setTextColor(R.id.bt_collection,ContextCompat.getColor(mContext,R.color.black))
            }
        }

    }

    inner class CommentAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
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


    override fun onBackPressed() {
        orientationUtils.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detailPlayer.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detailPlayer.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detailPlayer.currentPlayer.release()
        }
        orientationUtils.releaseListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}
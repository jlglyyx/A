package com.example.module_video.ui.activity

import android.content.res.Configuration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.module_video.R
import com.google.android.material.tabs.TabLayout
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.act_video_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Route(path = AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY)
class VideoItemActivity : BaseActivity() {


    lateinit var orientationUtils: OrientationUtils

    private val url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"

    private var isPause = false
    private var isPlay = false
    private var isFirstVisible = true
    private var isFirstGone = true

    override fun getLayout(): Int {
        return R.layout.act_video_item
    }

    override fun initData() {
    }

    override fun initView() {
        initVideo()
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
    }

    override fun initViewModel() {
    }

    private fun initRecyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CommentAdapter(R.layout.item_video_comment,mutableListOf<String>().apply {
            GlobalScope.launch(Dispatchers.IO) {
                for (i in 1..20) {
                    add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg")
                }
            }
        })

        collectionRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        collectionRecyclerView.adapter = CollectionAdapter(R.layout.item_video_collection,mutableListOf<String>().apply {

            GlobalScope.launch (Dispatchers.IO){
                for (i in 1..200) {
                    add("$i")
                }
            }

        }).apply {
            setOnItemClickListener { adapter, view, position ->
                initVideo()
                detailPlayer.startPlayLogic()
            }
        }
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
//                if (findFirstVisibleItemPosition >= 20) {
//                    tabLayout.getTabAt(1)?.select()
//                } else {
//                    tabLayout.getTabAt(0)?.select()
//                }
//                Log.i(TAG, "onScrolled: $findFirstVisibleItemPosition")
//            }
//    })

    }

    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("视频"))
        tabLayout.addTab(tabLayout.newTab().setText("评论"))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    recyclerView.smoothScrollToPosition(0)
                } else {
                    recyclerView.smoothScrollToPosition(20)
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
                    if (orientationUtils != null) {
                        orientationUtils.backToProtVideo()
                    }
                }
            }).setLockClickListener { view, lock ->
                if (orientationUtils != null) {
                    orientationUtils.isEnable = !lock
                }
            }.build(detailPlayer)


    }


    inner class CollectionAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: String) {
            helper.setText(R.id.bt_collection,item)
        }

    }
    inner class CommentAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: String) {

        }

    }


    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo()
        }
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
        if (orientationUtils != null) orientationUtils.releaseListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}
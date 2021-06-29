package com.example.module_video

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.constant.AppConstant
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

@Route(path = AppConstant.RoutePath.VIDEO_MAIN_ACTIVITY)
class MainActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {

    private val url =
        "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
    lateinit var detailPlayer:StandardGSYVideoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //增加title
        detailPlayer = findViewById(R.id.detailPlayer)
        detailPlayer.titleTextView.visibility = View.GONE
        detailPlayer.backButton.visibility = View.GONE

        initVideoBuilderMode()
    }

    override fun clickForFullScreen() {
    }

    override fun getDetailOrientationRotateAuto(): Boolean {
        return true
    }

    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return detailPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
        //内置封面可参考SampleCoverVideo

        //内置封面可参考SampleCoverVideo
        val imageView = ImageView(this)
        //loadCover(imageView, url)
        return GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setUrl(url)
            .setCacheWithPlay(true)
            .setVideoTitle(" ")
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setSeekRatio(1f)
    }
}

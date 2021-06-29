package com.example.module_video

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.constant.AppConstant
import com.shuyu.gsyvideoplayer.GSYBaseADActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener
import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.act_advertisement_video_activity.*

@Route(path = AppConstant.RoutePath.ADVERTISEMENT_VIDEO_ACTIVITY)
class AdvertisementVideoActivity : GSYBaseADActivityDetail<NormalGSYVideoPlayer, GSYADVideoPlayer>() {

    private val urlAd =
        "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4"

    private val urlAd2 =
        "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4"

    private val url =
        "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_advertisement_video_activity)
        //普通模式
        resolveNormalVideoUI()

        initVideoBuilderMode()

        detailPlayer.setLockClickListener { view, lock ->
            if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                orientationUtils.isEnable = !lock
            }
        }
        detailPlayer.isStartAfterPrepared = true
        detailPlayer.isReleaseWhenLossAudio = false

        detailPlayer.setGSYVideoProgressListener(object : GSYVideoProgressListener {
            private var preSecond = 0
            override fun onProgress(
                progress: Int,
                secProgress: Int,
                currentPosition: Int,
                duration: Int
            ) {
                //在5秒的时候弹出中间广告
                val currentSecond = currentPosition / 1000
                if (currentSecond >= 5 && currentSecond != preSecond) {
                    detailPlayer.currentPlayer.onVideoPause()
                    gsyadVideoOptionBuilder.setUrl(urlAd2).build(adPlayer)
                    startAdPlay()
                }
                preSecond = currentSecond
            }
        })
    }

    override fun getGSYADVideoOptionBuilder(): GSYVideoOptionBuilder {
        return getCommonBuilder().setUrl(urlAd)
    }

    override fun getDetailOrientationRotateAuto(): Boolean {
        return true
    }

    override fun getGSYVideoPlayer(): NormalGSYVideoPlayer {

        return detailPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.ic_launcher)
        return getCommonBuilder()
            .setUrl(url)
            //k.setThumbImageView(imageView)

    }

    override fun getGSYADVideoPlayer(): GSYADVideoPlayer {

        return adPlayer
    }

    override fun isNeedAdOnStart(): Boolean {
        return false
    }

    override fun onEnterFullscreen(url: String, vararg objects: Any) {
        super.onEnterFullscreen(url, *objects)
        //隐藏调全屏对象的返回按键
        val gsyVideoPlayer = objects[1] as GSYVideoPlayer
        gsyVideoPlayer.backButton.visibility = View.GONE
    }

    /**
     * 公用的视频配置
     */
    private fun getCommonBuilder(): GSYVideoOptionBuilder {
        return GSYVideoOptionBuilder()
            .setCacheWithPlay(true)
            .setVideoTitle(" ")
            .setFullHideActionBar(true)
            .setFullHideStatusBar(true)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false) //打开动画
            .setNeedLockFull(true)
            .setSeekRatio(1f)
    }

    private fun resolveNormalVideoUI() {
        //增加title
        detailPlayer.titleTextView.visibility = View.VISIBLE
        detailPlayer.backButton.visibility = View.VISIBLE
    }




}
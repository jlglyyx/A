package com.yang.module_video.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.yang.lib_common.adapter.TabAndViewPagerFragmentAdapter
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.module_video.R
import com.yang.module_video.helper.getVideoComponent
import com.yang.module_video.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.fra_video.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.VIDEO_FRAGMENT)
class VideoFragment : BaseLazyFragment() {
    @Inject
    lateinit var videoModule: VideoViewModel

    private lateinit var fragments: MutableList<Fragment>

    private lateinit var titles: MutableList<String>

    override fun getLayout(): Int {
        return R.layout.fra_video
    }

    override fun initData() {

        videoModule.getVideoTypeData()
        videoModule.mVideoTypeData.observe(this, Observer {

            it.forEach { videoTypeData ->
                titles.add(videoTypeData.name)
                fragments.add(
                    buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT)
                        .withString(AppConstant.Constant.TYPE,videoTypeData.type)
                        .navigation() as Fragment
                )
            }
            initViewPager()
            initTabLayout()

        })
        //videoModule.getVideoRepository()
    }

    override fun initView() {
        titles = mutableListOf()
        fragments = mutableListOf()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return videoModule.uC
    }

    override fun initViewModel() {
        getVideoComponent(this).inject(this)

    }


    private fun initViewPager() {

        viewPager.adapter = TabAndViewPagerFragmentAdapter(this, fragments, titles)
        viewPager.offscreenPageLimit = fragments.size
    }

    private fun initTabLayout() {

        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }
}
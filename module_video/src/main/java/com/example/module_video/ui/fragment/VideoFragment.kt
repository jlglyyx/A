package com.example.module_video.ui.fragment

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.adapter.MBannerAdapter
import com.example.lib_common.adapter.TabAndViewPagerFragmentAdapter
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.data.BannerBean
import com.example.lib_common.help.buildARouter
import com.example.module_video.R
import com.example.module_video.di.factory.VideoViewModelFactory
import com.example.module_video.helper.getVideoComponent
import com.example.module_video.viewmodel.VideoViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fra_video.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.VIDEO_FRAGMENT)
class VideoFragment : BaseLazyFragment() {
    @Inject
    lateinit var videoViewModelFactory: VideoViewModelFactory

    private lateinit var videoModule: VideoViewModel

    lateinit var fragments: MutableList<Fragment>

    private lateinit var titles: MutableList<String>

    override fun getLayout(): Int {
        return R.layout.fra_video
    }

    override fun initData() {

        //videoModule.getVideoRepository()
    }

    override fun initView() {
        fragments = mutableListOf<Fragment>().apply {
            add(buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.VIDEO_ITEM_FRAGMENT).navigation() as Fragment)
        }
        titles = mutableListOf<String>().apply {
            add("推荐")
            add("爱看")
            add("电视剧")
            add("电影")
            add("综艺")
            add("少儿")
            add("动漫")
        }
        initViewPager()
        initTabLayout()
        initBanner()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return videoModule.uC
    }

    override fun initViewModel() {
        getVideoComponent().inject(this)
        videoModule = getViewModel(videoViewModelFactory, VideoViewModel::class.java)

    }


    private fun initViewPager() {

        viewPager.adapter = TabAndViewPagerFragmentAdapter(this,fragments,titles)
        viewPager.offscreenPageLimit = fragments.size
    }

    private fun initTabLayout() {

        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()

    }

    private fun initBanner(){
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(MBannerAdapter(mutableListOf<BannerBean>().apply {
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
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
            })).indicator = CircleIndicator(requireContext())
    }




}
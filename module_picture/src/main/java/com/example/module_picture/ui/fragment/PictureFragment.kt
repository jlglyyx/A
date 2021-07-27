package com.example.module_picture.ui.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.adapter.MBannerAdapter
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.data.BannerBean
import com.example.lib_common.help.buildARouter
import com.example.module_picture.R
import com.example.module_picture.di.factory.PictureViewModelFactory
import com.example.module_picture.helper.getPictureComponent
import com.example.module_picture.viewmodel.PictureViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fra_picture.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.PICTURE_FRAGMENT)
class PictureFragment : BaseLazyFragment() {

    @Inject
    lateinit var pictureViewModelFactory: PictureViewModelFactory

    private lateinit var pictureModule: PictureViewModel

    lateinit var fragments: MutableList<Fragment>

    private lateinit var titles: MutableList<String>

    override fun getLayout(): Int {
        return R.layout.fra_picture
    }

    override fun initData() {

        //pictureModule.getPictureRepository()
    }

    override fun initView() {
        fragments = mutableListOf<Fragment>().apply {
            add(buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT).navigation() as Fragment)
            add(buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT).navigation() as Fragment)
        }
        titles = mutableListOf<String>().apply {
            add("推荐")
            add("风景")
            add("动漫")
            add("唯美")
            add("伤感")
            add("网络")
            add("可爱")
            add("二次元")
        }
        initViewPager()
        initTabLayout()
        initBanner()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return pictureModule.uC
    }

    override fun initViewModel() {
        getPictureComponent().inject(this)
        pictureModule = getViewModel(pictureViewModelFactory, PictureViewModel::class.java)

    }


    private fun initViewPager() {

        viewPager.adapter = MFragmentViewPagerAdapter(this)
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
            })).indicator = CircleIndicator(requireContext());
    }

    inner class MFragmentViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {

            return fragments[position]
        }
    }
}
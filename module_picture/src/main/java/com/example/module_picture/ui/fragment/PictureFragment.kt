package com.example.module_picture.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.adapter.MBannerAdapter
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.data.BannerBean
import com.example.lib_common.help.buildARouter
import com.example.module_picture.R
import com.example.module_picture.di.factory.PictureViewModelFactory
import com.example.module_picture.helper.getPictureComponent
import com.example.module_picture.model.ImageTypeData
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
        pictureModule.getImageTypeData()

        pictureModule.mImageTypeData.observe(this, Observer {

            it.forEach { imageTypeData ->
                titles.add(imageTypeData.name)
                fragments.add(
                    buildARouter(AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT)
                        .withString(AppConstant.Constant.TYPE,imageTypeData.type)
                        .navigation() as Fragment
                )
            }
            initViewPager()
            initTabLayout()
        })
    }

    override fun initView() {
        titles = mutableListOf()
        fragments = mutableListOf()
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

    private fun initBanner() {
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(MBannerAdapter(mutableListOf<BannerBean>().apply {
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
                add(BannerBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3859417927,1640776349&fm=11&gp=0.jpg"))
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
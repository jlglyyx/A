package com.example.module_picture.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.adapter.MBannerAdapter
import com.example.lib_common.adapter.TabAndViewPagerFragmentAdapter
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.data.BannerBean
import com.example.lib_common.util.buildARouter
import com.example.module_picture.R
import com.example.module_picture.helper.getPictureComponent
import com.example.module_picture.viewmodel.PictureViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fra_picture.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.PICTURE_FRAGMENT)
class PictureFragment : BaseLazyFragment() {

    @Inject
    lateinit var pictureModule: PictureViewModel

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
        getPictureComponent(this).inject(this)
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
            .indicator = CircleIndicator(requireContext());
    }


}
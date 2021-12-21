package com.yang.module_picture.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.adapter.TabAndViewPagerFragmentAdapter
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_picture.R
import com.yang.module_picture.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.fra_picture.*

@Route(path = AppConstant.RoutePath.PICTURE_FRAGMENT)
class PictureFragment : BaseLazyFragment() {

    @InjectViewModel(AppConstant.RoutePath.MODULE_PICTURE)
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
                        .withString(AppConstant.Constant.TYPE, imageTypeData.type)
                        .navigation() as Fragment
                )
            }
            initViewPager()
            initTabLayout()
        })
        commonToolBar.imageAddCallBack = object : CommonToolBar.ImageAddCallBack {
            override fun imageAddClickListener() {
                buildARouter(AppConstant.RoutePath.PICTURE_UPLOAD_ACTIVITY).navigation()
            }
        }

        commonToolBar.imageSearchCallBack = object : CommonToolBar.ImageSearchCallBack{
            override fun imageSearchClickListener() {
                buildARouter(AppConstant.RoutePath.PICTURE_SEARCH_ACTIVITY).withInt(
                    AppConstant.Constant.TYPE,
                    AppConstant.Constant.NUM_ZERO
                ).navigation()

            }
        }
        commonToolBar.ivSearch.visibility = View.VISIBLE

    }

    override fun initView() {
        titles = mutableListOf()
        fragments = mutableListOf()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return pictureModule.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }


    private fun initViewPager() {

        viewPager.adapter = TabAndViewPagerFragmentAdapter(this, fragments, titles)
        if (fragments.size != 0) {
            viewPager.offscreenPageLimit = fragments.size
        }
//        if(fragments.size != 0){
//            viewPager.offscreenPageLimit = if (fragments.size > 20){
//                20
//            }else{
//                fragments.size
//            }
//        }

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
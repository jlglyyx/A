package com.example.module_main.ui.menu.activity

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.adapter.TabAndViewPagerAdapter
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.util.buildARouter
import com.example.module_main.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.act_my_collection.*
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.activity_main.viewPager

/**
 * @Author Administrator
 * @ClassName MyCollectionActivity
 * @Description
 * @Date 2021/7/30 14:35
 */
@Route(path = AppConstant.RoutePath.MY_COLLECTION_ACTIVITY)
class MyCollectionActivity : BaseActivity() {

    lateinit var fragments: MutableList<Fragment>

    private lateinit var titles: MutableList<String>

    override fun getLayout(): Int {
        return R.layout.act_my_collection
    }

    override fun initData() {

        fragments = mutableListOf<Fragment>().apply {
            add(
                buildARouter(AppConstant.RoutePath.MY_COLLECTION_PICTURE_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                buildARouter(AppConstant.RoutePath.MY_COLLECTION_VIDEO_FRAGMENT)
                    .navigation() as Fragment
            )
        }
        titles = mutableListOf<String>().apply {
            add("图片")
            add("视频")
        }
    }

    override fun initView() {
        val stringExtra = intent.getStringExtra("name")
        commonToolBar.centerContent = stringExtra
        initViewPager()
        initTabLayout()
    }

    override fun initViewModel() {
    }



    private fun initViewPager() {

        viewPager.adapter = TabAndViewPagerAdapter(this,fragments,titles)
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


}
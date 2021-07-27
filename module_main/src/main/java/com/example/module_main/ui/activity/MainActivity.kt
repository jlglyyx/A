package com.example.module_main.ui.activity

import android.Manifest
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.buildARouter
import com.example.lib_common.util.getScreenPx
import com.example.lib_common.util.px2dip
import com.example.module_main.R
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.helper.getMainComponent
import com.example.module_main.ui.fragment.LeftFragment
import com.example.module_main.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.MAIN_ACTIVITY)
class MainActivity : BaseActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    lateinit var fragments: MutableList<Fragment>

    private lateinit var titles: MutableList<String>

    private var icon =
        arrayOf(R.drawable.iv_home, R.drawable.iv_video, R.drawable.iv_picture, R.drawable.iv_mine)
    private var selectIcon = arrayOf(
        R.drawable.iv_home_select,
        R.drawable.iv_video_select,
        R.drawable.iv_picture_select,
        R.drawable.iv_mine_select
    )


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        fragments = mutableListOf<Fragment>().apply {
            add(
                buildARouter(AppConstant.RoutePath.MAIN_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                buildARouter(AppConstant.RoutePath.VIDEO_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                buildARouter(AppConstant.RoutePath.PICTURE_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                buildARouter(AppConstant.RoutePath.MINE_FRAGMENT)
                    .navigation() as Fragment
            )
        }
        titles = mutableListOf<String>().apply {
            add("首页")
            add("视频")
            add("图片")
            add("我的")
        }
    }

    override fun initViewModel() {
        getMainComponent().inject(this)
        mainViewModel = getViewModel(mainViewModelFactory, MainViewModel::class.java)

    }

    override fun initView() {
        initDrawerLayout()
        initViewPager()
        initTabLayout()
        initPermission()
        initLeftFragment()
    }

    private fun initDrawerLayout() {
        val screenSize = getScreenPx(this)
        fragmentContainerView.post {
            val layoutParams = fragmentContainerView.layoutParams as DrawerLayout.LayoutParams
            fragmentContainerView.layoutParams = layoutParams
            layoutParams.rightMargin = fragmentContainerView.width - screenSize[0]
            fragmentContainerView.layoutParams = layoutParams
        }
    }


    private fun initViewPager() {

        viewPager.adapter = MFragmentViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = fragments.size

    }

    private fun initTabLayout() {

        TabLayoutMediator(
            tabLayout,
            viewPager,
            true,
            false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
                tab.setIcon(icon[position])
                if (position == 0) {
                    tab.setIcon(selectIcon[position])
                } else {
                    tab.setIcon(icon[position])
                }
            }).attach()

        tabLayout.post {
            viewPager.setPadding(0, 0, 0, tabLayout.height + 10f.px2dip(this))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.setIcon(icon[tab.position])
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.setIcon(selectIcon[tab.position])
            }

        })

    }

    private fun initPermission() {
        RxPermissions(this).requestEachCombined(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .subscribe {
                when {
                    it.granted -> {

                    }

                    it.shouldShowRequestPermissionRationale -> {

                    }
                    else -> {

                    }
                }
            }
    }

    private fun initLeftFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainerView,
            LeftFragment()
        ).commit()
    }


    inner class MFragmentViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {

            return fragments[position]
        }
    }


}
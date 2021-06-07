package com.example.module_main.ui.activity

import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.getBaseComponent
import com.example.lib_common.util.getScreenSize
import com.example.module_main.R
import com.example.module_main.di.component.DaggerMainComponent
import com.example.module_main.di.factory.MainViewModelFactory
import com.example.module_main.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@Route(path = AppConstant.RoutePath.A_MAIN)
class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var mainViewModel: MainViewModel

    lateinit var fragments: MutableList<Fragment>

    lateinit var titles: MutableList<String>


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        fragments = mutableListOf<Fragment>().apply {
            add(ARouter.getInstance().build(AppConstant.RoutePath.F_MAIN).navigation() as Fragment)
            add(ARouter.getInstance().build(AppConstant.RoutePath.F_MAIN).navigation() as Fragment)
            add(ARouter.getInstance().build(AppConstant.RoutePath.F_MAIN).navigation() as Fragment)
            add(ARouter.getInstance().build(AppConstant.RoutePath.F_MAIN).navigation() as Fragment)
        }
        titles = mutableListOf<String>().apply {
            add("首页")
            add("首页")
            add("首页")
            add("首页")
        }
    }

    override fun initViewModel() {
        DaggerMainComponent.builder().baseComponent(getBaseComponent()).build().inject(this)
        mainViewModel = getViewModel(mainViewModelFactory, MainViewModel::class.java)
        mainViewModel.getMainRepository()

    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mainViewModel.uC
    }

    override fun initView() {
        initDrawerLayout()
        initViewPager()
        initTabLayout()

    }

    private fun initDrawerLayout() {
        val screenSize = getScreenSize(this.window)
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

    }

    private fun initTabLayout() {

        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()

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
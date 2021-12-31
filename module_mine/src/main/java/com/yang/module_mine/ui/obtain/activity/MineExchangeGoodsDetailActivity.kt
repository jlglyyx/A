package com.yang.module_mine.ui.obtain.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.adapter.MBannerAdapter
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.BannerBean
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.module_mine.R
import com.yang.module_mine.adapter.MineObtainExchangeAdapter
import com.yang.module_mine.data.MineTurnoverData
import com.yang.module_mine.viewmodel.MineViewModel
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.act_mine_exchange_goods_detail.*
import kotlin.math.abs

/**
 * @Author Administrator
 * @ClassName MineExchangeActivity
 * @Description 我的兑换-物品详情
 * @Date 2021/9/14 10:39
 */
@Route(path = AppConstant.RoutePath.MINE_EXCHANGE_DETAIL_ACTIVITY)
class MineExchangeGoodsDetailActivity :BaseActivity() {

    @InjectViewModel
    lateinit var mineViewModel: MineViewModel

    private var alphaPercent = 0f

    private lateinit var mAdapter: MineObtainExchangeAdapter

    override fun getLayout(): Int {
        return R.layout.act_mine_exchange_goods_detail
    }

    override fun initData() {
        initBanner()
    }

    override fun initView() {
        initRecyclerView()
        initAppBarLayout()
        tv_exchange.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MINE_CREATE_ORDER_DETAIL_ACTIVITY).navigation()
        }
    }

    override fun initUIChangeLiveData(): UIChangeLiveData {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MineObtainExchangeAdapter(mutableListOf<MineTurnoverData>().apply {
            add(MineTurnoverData("签到了一天","100","+100"))
            add(MineTurnoverData("兑换了一块钱","99","-1"))
            add(MineTurnoverData("签到了一天","100","+100"))
            add(MineTurnoverData("兑换了一块钱","99","-1"))
            add(MineTurnoverData("签到了一天","100","+100"))
            add(MineTurnoverData("兑换了一块钱","99","-1"))
            add(MineTurnoverData("签到了一天","100","+100"))
            add(MineTurnoverData("兑换了一块钱","99","-1"))
            add(MineTurnoverData("签到了一天","100","+100"))
            add(MineTurnoverData("兑换了一块钱","99","-1"))
        })

        mAdapter.setOnItemClickListener { adapter, view, position ->

        }


        recyclerView.adapter = mAdapter
//        mineViewModel.mVideoData.observe(this, Observer {
//            when {
//                smartRefreshLayout.isRefreshing -> {
//                    smartRefreshLayout.finishRefresh()
//                    mAdapter.replaceData(it.list)
//                }
//                smartRefreshLayout.isLoading -> {
//                    smartRefreshLayout.finishLoadMore()
//                    if (pageNum != 1 && it.list.isEmpty()) {
//                        smartRefreshLayout.setNoMoreData(true)
//                    } else {
//                        smartRefreshLayout.setNoMoreData(false)
//                        mAdapter.addData(it.list)
//                    }
//                }
//                else -> {
//                    mAdapter.replaceData(it.list)
//                }
//            }
//        })

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
            .indicator = CircleIndicator(this)
    }

    private fun initAppBarLayout() {
        commonToolBar.tvCenterContent.alpha = alphaPercent
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            //滑动状态
            alphaPercent = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            if (alphaPercent <= 0.2f) {
                alphaPercent = 0f
            }
            if (alphaPercent >= 0.8f) {
                alphaPercent = 1f
            }
            commonToolBar.tvCenterContent.alpha = alphaPercent
        })
    }

}
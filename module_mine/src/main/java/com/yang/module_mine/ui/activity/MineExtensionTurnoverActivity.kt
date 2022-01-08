package com.yang.module_mine.ui.activity

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.module_mine.R
import com.yang.module_mine.adapter.MineExtensionTurnoverAdapter
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.view_normal_recyclerview.*

/**
 * @Author Administrator
 * @ClassName MineExtensionActivity
 * @Description 我的推广历史
 * @Date 2021/9/10 10:51
 */
@Route(path = AppConstant.RoutePath.MINE_EXTENSION_TURNOVER_ACTIVITY)
class MineExtensionTurnoverActivity : BaseActivity(), OnRefreshLoadMoreListener {

    @InjectViewModel
    lateinit var mineViewModel: MineViewModel
    private var pageNum = 1

    private lateinit var mTurnoverAdapter: MineExtensionTurnoverAdapter

    override fun getLayout(): Int {
        return R.layout.act_mine_extension
    }

    override fun initData() {
    }

    override fun initView() {
        initSmartRefreshLayout()
        initRecyclerView()
    }

    override fun initUIChangeLiveData(): UIChangeLiveData {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }

    private fun initSmartRefreshLayout() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(this)
        smartRefreshLayout.autoRefresh()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mTurnoverAdapter = MineExtensionTurnoverAdapter(null)
        recyclerView.adapter = mTurnoverAdapter
        mineViewModel.mMineExtensionTurnoverListLiveData.observe(this, Observer {
            when {
                smartRefreshLayout.isRefreshing -> {
                    smartRefreshLayout.finishRefresh()
                    if (it.size == 0) {
                        mineViewModel.showRecyclerViewEmptyEvent()
                    } else {
                        mTurnoverAdapter.replaceData(it)
                    }
                }
                smartRefreshLayout.isLoading -> {
                    smartRefreshLayout.finishLoadMore()
                    if (it.isNullOrEmpty()) {
                        smartRefreshLayout.setNoMoreData(true)
                    } else {
                        smartRefreshLayout.setNoMoreData(false)
                        mTurnoverAdapter.addData(it)
                    }
                }
                else -> {
                    if (it.size == 0) {
                        mineViewModel.showRecyclerViewEmptyEvent()
                    } else {
                        mTurnoverAdapter.replaceData(it)
                    }
                }
            }
        })

        registerRefreshAndRecyclerView(smartRefreshLayout, mTurnoverAdapter)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        mineViewModel.queryExtensionTurnover()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        mineViewModel.queryExtensionTurnover()
    }
}
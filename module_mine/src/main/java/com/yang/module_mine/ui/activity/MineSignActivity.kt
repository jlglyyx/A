package com.yang.module_mine.ui.activity

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
import com.yang.module_mine.adapter.MineObtainAdapter
import com.yang.module_mine.data.MineObtainData
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.view_normal_recyclerview.*

/**
 * @Author Administrator
 * @ClassName MineObtainActivity
 * @Description 我的签到历史
 * @Date 2021/9/10 10:51
 */
@Route(path = AppConstant.RoutePath.MINE_SIGN_ACTIVITY)
class MineSignActivity:BaseActivity() , OnRefreshLoadMoreListener {

    @InjectViewModel
    lateinit var mineViewModel: MineViewModel

    private var pageNum = 1

    private lateinit var mAdapter: MineObtainAdapter

    override fun getLayout(): Int {
        return R.layout.act_mine_sign
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
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MineObtainAdapter(mutableListOf<MineObtainData>().apply {
            add(MineObtainData("签到了一天","100","+100"))
            add(MineObtainData("兑换了一块钱","99","-1"))
            add(MineObtainData("签到了一天","100","+100"))
            add(MineObtainData("兑换了一块钱","99","-1"))
            add(MineObtainData("签到了一天","100","+100"))
            add(MineObtainData("兑换了一块钱","99","-1"))
            add(MineObtainData("签到了一天","100","+100"))
            add(MineObtainData("兑换了一块钱","99","-1"))
            add(MineObtainData("签到了一天","100","+100"))
            add(MineObtainData("兑换了一块钱","99","-1"))
        })
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

        registerRefreshAndRecyclerView(smartRefreshLayout, mAdapter)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        //mineViewModel.getVideoInfo("",pageNum,keyword,true)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        //mineViewModel.getVideoInfo("",pageNum,keyword,true)
    }
}
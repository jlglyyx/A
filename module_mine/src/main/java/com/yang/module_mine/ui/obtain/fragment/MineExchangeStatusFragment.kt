package com.yang.module_mine.ui.obtain.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.buildARouter
import com.yang.module_mine.R
import com.yang.module_mine.data.MineObtainExchangeData
import com.yang.module_mine.ui.obtain.adapter.MineExchangeStatusAdapter
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.view_normal_recyclerview.*

/**
 * @Author Administrator
 * @ClassName MineExchangeStatusFragment
 * @Description
 * @Date 2021/9/14 10:39
 */
@Route(path = AppConstant.RoutePath.MINE_EXCHANGE_STATUS_FRAGMENT)
class MineExchangeStatusFragment :BaseLazyFragment(), OnRefreshLoadMoreListener {

    @InjectViewModel
    lateinit var mineViewModel: MineViewModel

    private var pageNum = 1

    private lateinit var mAdapter: MineExchangeStatusAdapter

    var type:Int = -1

    override fun getLayout(): Int {
        return R.layout.act_mine_exchange_status
    }

    override fun initData() {

        arguments?.apply {
            type = getInt(AppConstant.Constant.TYPE,-1)
        }

        when(type){
            AppConstant.Constant.NUM_ZERO ->{

            }
            AppConstant.Constant.NUM_ONE ->{

            }
            AppConstant.Constant.NUM_TWO ->{

            }
            AppConstant.Constant.NUM_THREE ->{

            }
        }

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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = MineExchangeStatusAdapter(R.layout.item_mine_exchange_status,mutableListOf<MineObtainExchangeData>().apply {
            add(MineObtainExchangeData("签到了一天","100","+100"))
            add(MineObtainExchangeData("兑换了一块钱","99","-1"))
            add(MineObtainExchangeData("签到了一天","100","+100"))
            add(MineObtainExchangeData("兑换了一块钱","99","-1"))
            add(MineObtainExchangeData("签到了一天","100","+100"))
            add(MineObtainExchangeData("兑换了一块钱","99","-1"))
            add(MineObtainExchangeData("签到了一天","100","+100"))
            add(MineObtainExchangeData("兑换了一块钱","99","-1"))
            add(MineObtainExchangeData("签到了一天","100","+100"))
            add(MineObtainExchangeData("兑换了一块钱","99","-1"))
        })

        mAdapter.setOnItemClickListener { adapter, view, position ->

            buildARouter(AppConstant.RoutePath.MINE_ORDER_DETAIL_ACTIVITY).navigation()
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
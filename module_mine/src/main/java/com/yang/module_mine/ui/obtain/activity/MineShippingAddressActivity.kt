package com.yang.module_mine.ui.obtain.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_mine.R
import com.yang.module_mine.adapter.MineShippingAddressAdapter
import com.yang.module_mine.data.MineShippingAddressData
import kotlinx.android.synthetic.main.act_mine_shapping_address.*

/**
 * @Author Administrator
 * @ClassName MineShippingAddress
 * @Description 我的收货地址
 * @Date 2021/9/26 11:45
 */
@Route(path = AppConstant.RoutePath.MINE_SHIPPING_ADDRESS_ACTIVITY)
class MineShippingAddressActivity:BaseActivity() {

    lateinit var mAdapter: MineShippingAddressAdapter

    override fun getLayout(): Int {
        return R.layout.act_mine_shapping_address
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
        commonToolBar.tVRightCallBack = object :CommonToolBar.TVRightCallBack{
            override fun tvRightClickListener() {
                buildARouter(AppConstant.RoutePath.MINE_ADD_ADDRESS_ACTIVITY).navigation()
            }

        }
    }

    override fun initViewModel() {
    }

    private fun initRecyclerView(){
        mAdapter = MineShippingAddressAdapter(R.layout.item_mine_shipping_address, mutableListOf<MineShippingAddressData>().apply {
            add(MineShippingAddressData("1","湖北省武汉市武昌区湖北大学","张三","12345785236"))
            add(MineShippingAddressData("1","湖北省武汉市武昌区湖北大学","里斯","12345785236"))
            add(MineShippingAddressData("1","湖北省武汉市武昌区湖北大学","张三","12345785236"))
            add(MineShippingAddressData("1","湖北省武汉市武昌区湖北大学","张三","12345785236"))
            add(MineShippingAddressData("1","湖北省武汉市武昌区湖北大学","张三","12345785236"))
        })
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter.setOnItemClickListener { adapter, view, position ->
//            XPopup.Builder(this).asConfirm("确认兑换","抽纸一包",object : OnConfirmListener {
//                override fun onConfirm() {
//
//                }
//
//            }).show()
        }
    }
}
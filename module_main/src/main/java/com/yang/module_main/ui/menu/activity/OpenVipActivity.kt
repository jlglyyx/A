package com.yang.module_main.ui.menu.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_main.R
import com.yang.module_main.ui.menu.adapter.OpenVipAdapter
import com.yang.module_main.ui.menu.data.OpenVipData
import kotlinx.android.synthetic.main.act_open_vip.*

/**
 * @Author Administrator
 * @ClassName OpenVipActivity
 * @Description 开通会员
 * @Date 2021/9/28 10:47
 */
@Route(path = AppConstant.RoutePath.OPEN_VIP_ACTIVITY)
class OpenVipActivity:BaseActivity() {

    lateinit var mAdapter: OpenVipAdapter

    override fun getLayout(): Int {
        return R.layout.act_open_vip
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {
    }

    private fun initRecyclerView(){
        val mutableListOf = mutableListOf<OpenVipData>()
        mutableListOf.add(OpenVipData("试用会员",7,"9.9"))
        mutableListOf.add(OpenVipData("普通会员",30,"19.9"))
        mutableListOf.add(OpenVipData("试用会员",60,"29.9"))
        mutableListOf.add(OpenVipData("试用会员",90,"39.9"))
        mutableListOf.add(OpenVipData("试用会员",120,"49.9"))
        mutableListOf.add(OpenVipData("试用会员",150,"59.9"))

        mAdapter = OpenVipAdapter(R.layout.item_open_vip,mutableListOf)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
package com.yang.module_mine.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_mine.R
import com.yang.module_mine.adapter.MineEarnObtainAdapter
import com.yang.module_mine.data.MineEarnObtainData
import kotlinx.android.synthetic.main.act_mine_earn_obtain.*

/**
 * @Author Administrator
 * @ClassName MineEarnObtainActivity
 * @Description
 * @Date 2021/9/29 16:13
 */
@Route(path = AppConstant.RoutePath.MINE_EARN_OBTAIN_ACTIVITY)
class MineEarnObtainActivity:BaseActivity() {

    lateinit var mAdapter: MineEarnObtainAdapter

    override fun getLayout(): Int {
        return R.layout.act_mine_earn_obtain
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {
    }

    private fun initRecyclerView(){
        val mutableListOf = mutableListOf<MineEarnObtainData>()
        mutableListOf.add(MineEarnObtainData("每日签到+30",true))
        mutableListOf.add(MineEarnObtainData("限时推广+30",false))
        mutableListOf.add(MineEarnObtainData("观看视频+60",true))
        mutableListOf.add(MineEarnObtainData("观看视频+60",false))
        mutableListOf.add(MineEarnObtainData("观看视频+60",true))
        mutableListOf.add(MineEarnObtainData("观看视频+60",false))
        mutableListOf.add(MineEarnObtainData("观看视频+60",true))
        mutableListOf.add(MineEarnObtainData("观看视频+60",false))

        mAdapter = MineEarnObtainAdapter(R.layout.item_mine_earn_obtain,mutableListOf)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
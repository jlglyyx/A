package com.yang.module_mine.ui.obtain.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_mine.R

/**
 * @Author Administrator
 * @ClassName MineOrderDetailActivity
 * @Description
 * @Date 2021/10/11 9:38
 */
@Route(path = AppConstant.RoutePath.MINE_ORDER_DETAIL_ACTIVITY)
class MineOrderDetailActivity:BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_mine_order_detail
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {

    }
}
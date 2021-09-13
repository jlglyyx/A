package com.yang.module_mine.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_mine.R

/**
 * @Author Administrator
 * @ClassName MineObtainChageActivity
 * @Description
 * @Date 2021/9/13 17:16
 */
@Route(path = AppConstant.RoutePath.MINE_OBTAIN_EXCHANGE_ACTIVITY)
class MineObtainExchangeActivity:BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_mine_obtain_exchange
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}
package com.yang.module_mine.ui.obtain.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_mine.R
import kotlinx.coroutines.cancel
import java.text.DecimalFormat

/**
 * @Author Administrator
 * @ClassName MineOrderDetailActivity
 * @Description
 * @Date 2021/10/11 9:38
 */
@Route(path = AppConstant.RoutePath.MINE_ORDER_DETAIL_ACTIVITY)
class MineOrderDetailActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_mine_order_detail
    }

    override fun initData() {
        initTimer()
    }

    override fun initView() {
    }

    override fun initViewModel() {

    }

    private fun initTimer() {
//        lifecycleScope.launch(Dispatchers.Main) {
//            for (i in 60 * 60 downTo 1) {
//                tv_good_status.text = "${formatNumber(i / 60 / 60 % 60)}:${formatNumber(i / 60 % 60)}:${formatNumber(i % 60)}"
//                delay(1000)
//            }
//        }
    }

    private fun formatNumber(i:Int):String{
        return DecimalFormat("00").format(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}
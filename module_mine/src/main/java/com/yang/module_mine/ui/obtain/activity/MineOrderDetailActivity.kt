package com.yang.module_mine.ui.obtain.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_mine.R
import kotlinx.android.synthetic.main.act_mine_create_order_detail.*
import kotlinx.coroutines.cancel

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

    }

    override fun initView() {
        Glide.with(this)
            .load("https://img.alicdn.com/bao/uploaded/i2/2209667639897/O1CN015Oh5X32MysY6ea4fc_!!0-item_pic.jpg_200x200q90.jpg_.webp")
            .into(iv_image)
    }

    override fun initViewModel() {

    }


    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}
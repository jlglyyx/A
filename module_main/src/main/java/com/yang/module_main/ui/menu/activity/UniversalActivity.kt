package com.yang.module_main.ui.menu.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_main.R

/**
 * @Author Administrator
 * @ClassName UniversalActivity
 * @Description 通用
 * @Date 2021/9/28 11:17
 */
@Route(path = AppConstant.RoutePath.UNIVERSAL_ACTIVITY)
class UniversalActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_universal
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}
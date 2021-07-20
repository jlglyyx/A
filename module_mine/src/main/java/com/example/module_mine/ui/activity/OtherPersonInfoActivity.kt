package com.example.module_mine.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.module_mine.R

@Route(path = AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
class OtherPersonInfoActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_other_person_info
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}
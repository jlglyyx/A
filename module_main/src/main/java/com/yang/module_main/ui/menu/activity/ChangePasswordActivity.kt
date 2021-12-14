package com.yang.module_main.ui.menu.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_main.R
import kotlinx.android.synthetic.main.act_change_password.*

/**
 * @Author Administrator
 * @ClassName ChangePasswordActivity
 * @Description 修改密码
 * @Date 2021/9/28 10:47
 */
@Route(path = AppConstant.RoutePath.CHANGE_PASSWORD_ACTIVITY)
class ChangePasswordActivity:BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_change_password
    }

    override fun initData() {
    }

    override fun initView() {

        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack{
            override fun tvRightClickListener() {
                finish()
            }

        }
    }

    override fun initViewModel() {
    }
}
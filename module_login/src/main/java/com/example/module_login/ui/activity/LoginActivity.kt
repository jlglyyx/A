package com.example.module_login.ui.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.util.clicks
import com.example.module_login.R
import kotlinx.android.synthetic.main.act_login.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Route(path = AppConstant.RoutePath.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_login
    }

    override fun initData() {
    }

    override fun initView() {

        bt_login.clicks().subscribe {
            ARouter.getInstance().build(AppConstant.RoutePath.MAIN_ACTIVITY).navigation()
        }

        tv_verification_code.clicks().subscribe {
            initTimer()
        }
        tv_to_register.clicks().subscribe {
            ARouter.getInstance().build(AppConstant.RoutePath.REGISTER_ACTIVITY).navigation()
        }
    }

    override fun initViewModel() {
    }

    private fun initTimer(){
        lifecycleScope.launch {
            tv_verification_code.isClickable = false
            for (i in 60 downTo 0){
                tv_verification_code.text = "${i}秒后获取验证码"
                delay(1000)
            }
            tv_verification_code.isClickable = true
            tv_verification_code.text = "重新获取验证码"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.cancel()
    }
}
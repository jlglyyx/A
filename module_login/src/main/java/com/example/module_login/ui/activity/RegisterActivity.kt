package com.example.module_login.ui.activity

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.base.ui.activity.BaseActivity
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.util.clicks
import com.example.module_login.R
import kotlinx.android.synthetic.main.act_register.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = AppConstant.RoutePath.REGISTER_ACTIVITY)
class RegisterActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.act_register
    }

    override fun initData() {
    }

    override fun initView() {
        bt_register.setOnClickListener {
            finish()
        }
        tv_verification_code.clicks().subscribe {
            initTimer()
        }
        tv_to_login.clicks().subscribe {
            finish()
        }
    }

    override fun initViewModel() {
    }

    private fun initTimer() {
        lifecycleScope.launch {
            tv_verification_code.isClickable = false
            for (i in 60 downTo 0) {
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
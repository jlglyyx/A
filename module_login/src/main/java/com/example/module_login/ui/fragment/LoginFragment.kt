package com.example.module_login.ui.fragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.module_login.R
import kotlinx.android.synthetic.main.fra_login.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_login
    }

    override fun initData() {
    }

    override fun initView() {
        bt_login.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_login_to_register)
        }

        tv_verification_code.setOnClickListener {
            initTimer()
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

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
    }
}
package com.example.module_login.ui.fragment

import androidx.navigation.Navigation
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.module_login.R
import kotlinx.android.synthetic.main.fra_login.*

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
    }

    override fun initViewModel() {
    }
}
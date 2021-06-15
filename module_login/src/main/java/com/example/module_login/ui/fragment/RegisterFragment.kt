package com.example.module_login.ui.fragment

import androidx.navigation.Navigation
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.module_login.R
import kotlinx.android.synthetic.main.fra_register.*

class RegisterFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_register
    }

    override fun initData() {
    }

    override fun initView() {
        bt_register.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_register_to_login)
        }
    }

    override fun initViewModel() {
    }
}
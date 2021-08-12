package com.example.module_main.ui.menu.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.util.buildARouter
import com.example.lib_common.util.clicks
import com.example.lib_common.util.removeAllActivity
import com.example.lib_common.util.getDefaultMMKV
import com.example.lib_common.util.getUserInfo
import com.example.module_main.R
import kotlinx.android.synthetic.main.fra_left.*
import kotlinx.android.synthetic.main.fra_main.*

@Route(path = AppConstant.RoutePath.LEFT_FRAGMENT)
class LeftFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_left
    }

    override fun initData() {
    }

    override fun initView() {
        val userInfo = getUserInfo()
        Glide.with(this).load(userInfo?.userImage).into(siv_head)
        tv_name.text = userInfo?.userName
        siv_head.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).navigation()
        }
        tv_my_push.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MY_PUSH_ACTIVITY).navigation()
        }
        tv_my_collection.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MY_COLLECTION_ACTIVITY).withString("name","我的收藏").navigation()
        }
        tv_my_down.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MY_COLLECTION_ACTIVITY)
                .withString("name","我的下载").navigation()
        }
        tv_setting.clicks().subscribe {
            removeAllActivity()
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).navigation()
        }

        tv_login_out.clicks().subscribe {
            getDefaultMMKV().clearAll()
        }
    }

    override fun initViewModel() {
    }

}
package com.example.module_main.ui.menu.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.buildARouter
import com.example.lib_common.util.clicks
import com.example.module_main.R
import kotlinx.android.synthetic.main.fra_left.*
@Route(path = AppConstant.RoutePath.LEFT_FRAGMENT)
class LeftFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_left
    }

    override fun initData() {
    }

    override fun initView() {
        Glide.with(this).load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg").into(siv_head)
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
            buildARouter(AppConstant.RoutePath.MY_COLLECTION_ACTIVITY).withString("name","我的下载").navigation()
        }
    }

    override fun initViewModel() {
    }

}
package com.yang.module_main.ui.menu.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.bumptech.glide.Glide
import com.yang.lib_common.base.ui.fragment.BaseFragment
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.*
import com.yang.module_main.R
import kotlinx.android.synthetic.main.fra_left.*

@Route(path = AppConstant.RoutePath.LEFT_FRAGMENT)
class LeftFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fra_left
    }

    override fun initData() {
    }

    override fun initView() {
        val userInfo = getUserInfo()
        Glide.with(this).load(userInfo?.userImage).error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder).into(siv_head)
        tv_name.text = userInfo?.userName?:"张三"

        LocationUtil(requireContext()).apply {
            lifecycle.addObserver(this)
            locationListener = object :LocationUtil.LocationListener{
                override fun onLocationListener(aMapLocation: AMapLocation) {
                    tv_location.text = "${aMapLocation.city}${aMapLocation.district}${aMapLocation.aoiName}"
                }

            }
        }.startLocation()


        lifecycle.addObserver(isv_image)

        siv_head.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY).withString(AppConstant.Constant.ID,userInfo?.id).navigation()
        }
        tv_open_vip.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.OPEN_VIP_ACTIVITY).navigation()
        }
        tv_my_push.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MY_PUSH_ACTIVITY).navigation()
        }
        tv_my_collection.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MY_COLLECTION_ACTIVITY).withString(AppConstant.Constant.NAME,"我的收藏").navigation()
        }
        tv_my_down.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MY_COLLECTION_ACTIVITY).withString(AppConstant.Constant.NAME,"我的下载").navigation()
        }
        tv_privacy.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.PRIVACY_ACTIVITY).navigation()
        }
        tv_universal.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.UNIVERSAL_ACTIVITY).navigation()
        }
        tv_accessibility.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.ACCESSIBILITY_ACTIVITY).navigation()
        }
        tv_setting.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.SETTING_ACTIVITY).navigation()
        }
        tv_about_and_help.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.ABOUT_AND_HELP_ACTIVITY).navigation()
        }

        tv_login_out.clicks().subscribe {
            getDefaultMMKV().clearAll()
            removeAllActivity()
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY).withTransition(
                R.anim.fade_in,
                R.anim.fade_out
            ).navigation(requireActivity())
        }
        tv_webView.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.WEB_VIEW_ACTIVITY).navigation()
        }
        tv_upload.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.UPLOAD_ACTIVITY).navigation()
        }
    }

    override fun initViewModel() {
    }

}
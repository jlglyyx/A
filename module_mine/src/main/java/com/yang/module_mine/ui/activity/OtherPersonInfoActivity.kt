package com.yang.module_mine.ui.activity

import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getUserInfo
import com.yang.lib_common.widget.CommonToolBar
import com.yang.module_mine.R
import kotlinx.android.synthetic.main.act_other_person_info.*
import kotlin.math.abs

@Route(path = AppConstant.RoutePath.OTHER_PERSON_INFO_ACTIVITY)
class OtherPersonInfoActivity : BaseActivity() {

    private var alphaPercent = 0f

    private lateinit var tvCenterContent:TextView



    override fun getLayout(): Int {
        return R.layout.act_other_person_info
    }

    override fun initData() {
        val id = intent.getStringExtra(AppConstant.Constant.ID)
        val userInfo = getUserInfo()
        Glide.with(this).load(userInfo?.userImage).into(siv_img)
    }

    override fun initView() {
        tvCenterContent = commonToolBar.findViewById(R.id.tv_centerContent)
        initAppBarLayout()
        commonToolBar.tVRightCallBack = object : CommonToolBar.TVRightCallBack{
            override fun tvRightClickListener() {
                buildARouter(AppConstant.RoutePath.CHANGE_USER_INFO_ACTIVITY).navigation()
            }

        }
    }

    override fun initViewModel() {

    }


    private fun initAppBarLayout() {
        tvCenterContent.alpha = alphaPercent
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            //滑动状态
            alphaPercent =
                abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            if (alphaPercent <= 0.2f) {
                alphaPercent = 0f
            }
            if (alphaPercent >= 0.8f) {
                alphaPercent = 1f
            }
            tvCenterContent.alpha = alphaPercent
        })
    }





}
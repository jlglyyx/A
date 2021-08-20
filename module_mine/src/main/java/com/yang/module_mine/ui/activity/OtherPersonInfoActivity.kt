package com.yang.module_mine.ui.activity

import android.util.Log
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.module_mine.R
import com.google.android.material.appbar.AppBarLayout
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
    }

    override fun initView() {
        tvCenterContent = commonToolBar.findViewById(R.id.tv_centerContent)
        initAppBarLayout()
    }

    override fun initViewModel() {
    }


    private fun initAppBarLayout() {
        tvCenterContent.alpha = alphaPercent
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            //滑动状态
            alphaPercent =
                abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            Log.i(TAG, "onOffsetChanged: $verticalOffset   ${appBarLayout.totalScrollRange}  $alphaPercent")
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
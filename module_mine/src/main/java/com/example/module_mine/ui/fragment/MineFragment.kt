package com.example.module_mine.ui.fragment

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.constant.AppConstant
import com.example.module_mine.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fra_mine.*
import kotlin.math.abs

@Route(path = AppConstant.RoutePath.MINE_FRAGMENT)
class MineFragment : BaseLazyFragment() {

    private var alphaPercent = 0f

    override fun getLayout(): Int {
        return R.layout.fra_mine
    }

    override fun initView() {
        initAppBarLayout()
        Glide.with(this)
            .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            .into(siv_img)
        Glide.with(this)
            .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            .into(siv_toolbar_img)
    }

    override fun initData() {

    }

    override fun initViewModel() {

    }


    private fun initAppBarLayout() {
        siv_toolbar_img.alpha = alphaPercent
        tv_toolbar_name.alpha = alphaPercent
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            //滑动状态
            alphaPercent =
                abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            Log.i(
                TAG,
                "onOffsetChanged: $verticalOffset   ${appBarLayout.totalScrollRange}  $alphaPercent"
            )
            if (alphaPercent <= 0.2f) {
                alphaPercent = 0f
            }
            if (alphaPercent >= 0.8f) {
                alphaPercent = 1f
            }
            siv_toolbar_img.alpha = alphaPercent
            tv_toolbar_name.alpha = alphaPercent
            tv_toolbar_title.alpha = (1f - alphaPercent)
        })
    }

}
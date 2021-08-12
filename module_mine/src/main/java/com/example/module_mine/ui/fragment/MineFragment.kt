package com.example.module_mine.ui.fragment

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.lib_common.adapter.NormalImageAdapter
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.util.buildARouter
import com.example.lib_common.util.clicks
import com.example.module_mine.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fra_mine.*
import kotlin.math.abs

@Route(path = AppConstant.RoutePath.MINE_FRAGMENT)
class MineFragment : BaseLazyFragment() {

    private lateinit var normalImageAdapter: NormalImageAdapter<String>

    private var alphaPercent = 0f

    override fun getLayout(): Int {
        return R.layout.fra_mine
    }

    override fun initView() {
        initAppBarLayout()
        initRecyclerView()
        Glide.with(this)
            .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            .into(siv_img)
        Glide.with(this)
            .load("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            .into(siv_toolbar_img)

        tv_name.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY)
                .navigation()
        }
        tv_toolbar_name.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY)
                .navigation()
        }
    }

    override fun initData() {

    }

    override fun initViewModel() {

    }


    private fun initRecyclerView() {
        normalImageAdapter = NormalImageAdapter(mutableListOf<String>().apply {
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=1834859148,419625166&fm=26&fmt=auto&gp=0.jpg")
        },R.layout.item_view_history_image)
        recyclerView.adapter = normalImageAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

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
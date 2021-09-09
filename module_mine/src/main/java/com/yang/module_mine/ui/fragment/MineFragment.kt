package com.yang.module_mine.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.yang.lib_common.adapter.NormalImageAdapter
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.transform.BlurTransformation
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.getUserInfo
import com.yang.module_mine.R
import com.yang.module_mine.helper.getMineComponent
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fra_mine.*
import javax.inject.Inject
import kotlin.math.abs

@Route(path = AppConstant.RoutePath.MINE_FRAGMENT)
class MineFragment : BaseLazyFragment() {

    @Inject
    lateinit var mineViewModel: MineViewModel

    private lateinit var normalImageAdapter: NormalImageAdapter<String>

    private var alphaPercent = 0f

    override fun getLayout(): Int {
        return R.layout.fra_mine
    }

    override fun initView() {
        initAppBarLayout()
        initRecyclerView()
        val userInfo = getUserInfo()
        tv_toolbar_name.text = userInfo?.userName
        tv_name.text = userInfo?.userName
        Glide.with(this)
            .load(userInfo?.userImage)
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(siv_img)
        Glide.with(this)
            .load(userInfo?.userImage)
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(siv_toolbar_img)
        Glide.with(this)
            .load("https://img1.baidu.com/it/u=584562345,1740343441&fm=26&fmt=auto&gp=0.jpg")
            .apply(RequestOptions.bitmapTransform(BlurTransformation(requireContext())))
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(iv_bg)
        tv_name.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY)
                .navigation()
        }
        tv_toolbar_name.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.LOGIN_ACTIVITY)
                .navigation()
        }
        ll_view_history.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.VIEW_HISTORY_ACTIVITY)
                .navigation()
        }

    }

    override fun initData() {

    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        getMineComponent(this).inject(this)
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
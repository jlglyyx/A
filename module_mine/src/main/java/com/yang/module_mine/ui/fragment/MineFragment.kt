package com.yang.module_mine.ui.fragment

import android.os.Environment
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.appbar.AppBarLayout
import com.yang.apt_annotation.annotain.InjectViewModel
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.proxy.InjectViewModelProxy
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.clicks
import com.yang.lib_common.util.getFilePath
import com.yang.lib_common.util.getUserInfo
import com.yang.module_mine.R
import com.yang.module_mine.data.ViewHistoryData
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fra_mine.*
import kotlin.math.abs

@Route(path = AppConstant.RoutePath.MINE_FRAGMENT)
class MineFragment : BaseLazyFragment() {

    @InjectViewModel
    lateinit var mineViewModel: MineViewModel

    private lateinit var mAdapter: MAdapter

    private var alphaPercent = 0f

    override fun getLayout(): Int {
        return R.layout.fra_mine
    }

    override fun initView() {
        initAppBarLayout()
        initRecyclerView()
        initViewClickListener()

        lifecycle.addObserver(iv_bg)
    }

    override fun initData() {
        val userInfo = getUserInfo()
        tv_toolbar_name.text = userInfo?.userName ?: "点击登录"
        tv_name.text = userInfo?.userName ?: "点击登录"
        Glide.with(this)
            .load(
                userInfo?.userImage
                    ?: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F39%2Fb7%2F53%2F39b75357f98675e2d6d5dcde1fb805a3.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642840086&t=2a7574a5d8ecc96669ac3e050fe4fd8e"
            )
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(siv_img)
        Glide.with(this)
            .load(
                userInfo?.userImage
                    ?: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2F39%2Fb7%2F53%2F39b75357f98675e2d6d5dcde1fb805a3.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642840086&t=2a7574a5d8ecc96669ac3e050fe4fd8e"
            )
            .error(R.drawable.iv_image_error)
            .placeholder(R.drawable.iv_image_placeholder)
            .into(siv_toolbar_img)
    }

    override fun initUIChangeLiveData(): UIChangeLiveData {
        return mineViewModel.uC
    }

    override fun initViewModel() {
        InjectViewModelProxy.inject(this)
    }


    private fun initRecyclerView() {

        val mutableListOf = mutableListOf<ViewHistoryData>()
        val picturePath = getFilePath()

        picturePath.forEach {
            mutableListOf.add(ViewHistoryData("1", it))
        }
        val videoPath = getFilePath("${Environment.getExternalStorageDirectory()}/MFiles/video")
        videoPath.forEach {
            mutableListOf.add(ViewHistoryData("2", it))
        }

        mAdapter = MAdapter(R.layout.item_mine_view_history_image, mutableListOf.subList(0, 10)).apply {
            setOnItemClickListener { adapter, view, position ->
                val item = mAdapter.getItem(position)
                item?.let {
                    if (it.type == "1"){
                        buildARouter(AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
                            .withString(AppConstant.Constant.ID, it.id)
                            .navigation()
                    }else{
                        buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY)
                            .withString(AppConstant.Constant.URL, it.filePath)
                            .navigation()
                    }
                }

            }
        }
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }


    private fun initAppBarLayout() {
        siv_toolbar_img.alpha = alphaPercent
        tv_toolbar_name.alpha = alphaPercent
        toolbar.alpha = 0f
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
            toolbar.alpha = alphaPercent
        })
    }


    private fun initViewClickListener() {
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
        ll_my_obtain.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MINE_OBTAIN_ACTIVITY)
                .navigation()
        }
        ll_my_sing.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MINE_SIGN_ACTIVITY)
                .navigation()
        }
        ll_my_extension.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MINE_EXTENSION_ACTIVITY)
                .navigation()
        }
        ll_obtain_integral.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MINE_EARN_OBTAIN_ACTIVITY)
                .navigation()
        }
        ll_time_limit_extension.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MINE_LIMIT_TIME_EXTENSION_ACTIVITY)
                .navigation()
        }
        ll_exchange_obtain.clicks().subscribe {
            buildARouter(AppConstant.RoutePath.MINE_OBTAIN_EXCHANGE_ACTIVITY)
                .navigation()
        }
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<ViewHistoryData>) :
        BaseQuickAdapter<ViewHistoryData, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: ViewHistoryData) {
            val ivImage = helper.getView<ImageView>(R.id.iv_image)
            if (item.type == "2") {
                Glide.with(ivImage)
                    .setDefaultRequestOptions(RequestOptions().frame(1000))
                    .load(item.filePath)
                    .centerCrop()
                    .error(R.drawable.iv_image_error)
                    .placeholder(R.drawable.iv_image_placeholder)
                    .into(ivImage)
            } else {
                Glide.with(ivImage).load(item.filePath)
                    .centerCrop()
                    .error(R.drawable.iv_image_error)
                    .placeholder(R.drawable.iv_image_placeholder)
                    .into(ivImage)
            }
        }
    }

}
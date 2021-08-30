package com.yang.module_main.ui.menu.fragment

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.lib_common.base.ui.fragment.BaseFragment
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getFilePath
import com.yang.module_main.R
import kotlinx.android.synthetic.main.view_normal_recyclerview.*

/**
 * @Author Administrator
 * @ClassName MyCollectionVideoFragment
 * @Description
 * @Date 2021/7/30 14:36
 */
@Route(path = AppConstant.RoutePath.MY_COLLECTION_VIDEO_FRAGMENT)
class MyCollectionVideoFragment : BaseFragment() {

    private lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.fra_my_collection_video
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = MAdapter(R.layout.item_menu_my_collection_picture, mutableListOf()).apply {
            setOnItemClickListener { adapter, view, position ->
                buildARouter(AppConstant.RoutePath.VIDEO_ITEM_ACTIVITY)
                    .navigation()
            }
        }
        recyclerView.adapter = mAdapter
        val filePath = getFilePath("/MFiles/video")
        mAdapter.replaceData(filePath)
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: String) {
            val ivImage = helper.getView<ImageView>(R.id.iv_image)
            Glide.with(ivImage).setDefaultRequestOptions(RequestOptions().frame(1000).fitCenter()).load(item).into(ivImage)
        }
    }
}
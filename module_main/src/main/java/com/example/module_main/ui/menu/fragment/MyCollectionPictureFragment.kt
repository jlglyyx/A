package com.example.module_main.ui.menu.fragment

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseFragment
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.help.buildARouter
import com.example.lib_common.util.getFilePath
import com.example.module_main.R
import kotlinx.android.synthetic.main.view_normal_recyclerview.*

/**
 * @Author Administrator
 * @ClassName MyCollectionPictureFragment
 * @Description
 * @Date 2021/7/30 14:36
 */
@Route(path = AppConstant.RoutePath.MY_COLLECTION_PICTURE_FRAGMENT)
class MyCollectionPictureFragment : BaseFragment() {

    private lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.fra_my_collection_picture
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
                buildARouter(AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
                    .withString(AppConstant.Constant.ID, "0").navigation()
            }
        }
        recyclerView.adapter = mAdapter
        val filePath = getFilePath()
        mAdapter.replaceData(filePath)
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: String) {
            val ivImage = helper.getView<ImageView>(R.id.iv_image)
            Glide.with(ivImage).load(item).into(ivImage)
        }
    }

}
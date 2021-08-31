package com.yang.module_mine.ui.activity

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.lib_common.base.ui.activity.BaseActivity
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.util.buildARouter
import com.yang.lib_common.util.getFilePath
import com.yang.module_mine.R
import kotlinx.android.synthetic.main.view_normal_recyclerview.*

/**
 * @Author Administrator
 * @ClassName HistoryActivity
 * @Description
 * @Date 2021/8/31 10:44
 */
@Route(path = AppConstant.RoutePath.VIEW_HISTORY_ACTIVITY)
class ViewHistoryActivity:BaseActivity() {

    private lateinit var mAdapter: MAdapter
    override fun getLayout(): Int {
        return R.layout.act_view_history
    }

    override fun initData() {
    }

    override fun initView() {
        initRecyclerView()
    }

    override fun initViewModel() {
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MAdapter(R.layout.item_view_history, mutableListOf()).apply {
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
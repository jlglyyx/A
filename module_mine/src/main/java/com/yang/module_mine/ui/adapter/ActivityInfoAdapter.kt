package com.yang.module_mine.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.module_mine.R
import com.yang.module_mine.ui.data.ActivityInfoData

/**
 * @Author Administrator
 * @ClassName ActivityInfoAdapter
 * @Description
 * @Date 2021/8/27 14:25
 */
class ActivityInfoAdapter(layoutResId: Int,data: MutableList<ActivityInfoData>) : BaseQuickAdapter<ActivityInfoData, BaseViewHolder>(layoutResId,data) {

    override fun convert(helper: BaseViewHolder, item: ActivityInfoData) {
        helper.setText(R.id.tv_name,item.key)
    }
}
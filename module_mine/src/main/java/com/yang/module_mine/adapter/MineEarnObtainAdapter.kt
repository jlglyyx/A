package com.yang.module_mine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.module_mine.R
import com.yang.module_mine.data.MineEarnObtainData

/**
 * @Author Administrator
 * @ClassName MineEarnObtainAdapter
 * @Description
 * @Date 2021/9/29 16:49
 */
class MineEarnObtainAdapter(layoutResId: Int, data: MutableList<MineEarnObtainData>?) :
    BaseQuickAdapter<MineEarnObtainData, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MineEarnObtainData) {

        helper.setText(R.id.tv_content, item.content)
            .setText(R.id.tv_finish, if (item.finnish) "已完成" else "去完成")
    }
}
package com.yang.module_main.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.lib_common.data.MediaInfoBean
import com.yang.module_main.R
import java.text.DecimalFormat

/**
 * @Author Administrator
 * @ClassName PictureSelectAdapter
 * @Description
 * @Date 2021/8/6 14:39
 */
class PictureSelectAdapter(
    layoutResId: Int,
    data: MutableList<MediaInfoBean>,
    private val showSelect: Boolean = true
) :
    BaseQuickAdapter<MediaInfoBean, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: MediaInfoBean) {
        val ivImg = helper.getView<ImageView>(R.id.iv_image)
        if (showSelect) {
            helper.addOnClickListener(R.id.cl_cb)
            if (item.fileType == 1) {
                helper.setText(R.id.tv_time, secToTime(item.fileDurationTime.toString()))
                helper.setVisible(R.id.tv_time, true)
            } else {
                helper.setVisible(R.id.tv_time, false)
            }
            if (item.isSelect) {
                helper.setText(R.id.cb_image, "${item.selectPosition}")
                helper.setBackgroundRes(R.id.cb_image, R.drawable.shape_select_picture)
            } else {
                helper.setText(R.id.cb_image, "")
                helper.setBackgroundRes(R.id.cb_image, R.drawable.shape_no_select_picture)
            }
        } else {
            helper.setVisible(R.id.cl_cb, false)
            helper.setVisible(R.id.tv_time, false)
        }

        if (item.fileType == 1) {
            Glide.with(ivImg)
                .setDefaultRequestOptions(RequestOptions().frame(1000).centerCrop())
                .load(item.filePath)
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(ivImg)
            helper.setVisible(R.id.iv_play, true)
        } else {
            Glide.with(ivImg)
                .load(item.filePath)
                .centerCrop()
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(ivImg)
            helper.setVisible(R.id.iv_play, false)
        }

    }

    private fun secToTime(data: String): String? {
        val time = data.toInt() / 1000
        var timeStr: String?
        if (time <= 0) return "00:00:00" else {
            timeStr =
                "${unitFormat(time / 60 / 60 % 60)}:${unitFormat(time / 60 % 60)}:${unitFormat(time % 60)}"
        }
        return timeStr
    }

    private fun unitFormat(i: Int): String {
        return DecimalFormat("00").format(i)
    }
}
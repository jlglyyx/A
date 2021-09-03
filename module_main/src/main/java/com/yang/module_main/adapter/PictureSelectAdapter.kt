package com.yang.module_main.adapter

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yang.lib_common.data.MediaInfoBean
import com.yang.module_main.R

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

    var viewOnTouchListener:ViewOnTouchListener? = null
    interface ViewOnTouchListener{
        fun setOnTouchListener(v: View, event: MotionEvent,adapter: BaseQuickAdapter<MediaInfoBean, BaseViewHolder>,position:Int):Boolean
    }

    override fun convert(helper: BaseViewHolder, item: MediaInfoBean) {
        val ivImg = helper.getView<ImageView>(R.id.iv_image)
        if (showSelect) {

            helper.addOnClickListener(R.id.cl_cb)
            if (item.fileType == 1) {
                Glide.with(ivImg)
                    .setDefaultRequestOptions(RequestOptions().frame(1000).centerCrop())
                    .load(item.filePath)
                    .into(ivImg)

                helper.setText(R.id.tv_time, secToTime(item.fileDurationTime.toString()))
                helper.setVisible(R.id.tv_time, true)
                helper.setVisible(R.id.iv_play, true)
            } else {
                Glide.with(ivImg)
                    .load(item.filePath)
                    .centerCrop()
                    .into(ivImg)
                helper.setVisible(R.id.tv_time, false)
                helper.setVisible(R.id.iv_play, false)
            }
            if (item.isSelect) {
                helper.setText(R.id.cb_image, "${item.selectPosition}")
                helper.setBackgroundRes(R.id.cb_image,R.drawable.shape_select_picture)
            } else {
                helper.setText(R.id.cb_image, "")
                helper.setBackgroundRes(R.id.cb_image,R.drawable.shape_no_select_picture)
            }

        } else {
            if (item.fileType == 1) {
                Glide.with(ivImg)
                    .setDefaultRequestOptions(RequestOptions().frame(1000).centerCrop())
                    .load(item.filePath)
                    .into(ivImg)
                helper.setVisible(R.id.iv_play, true)
            } else {
                Glide.with(ivImg)
                    .load(item.filePath)
                    .centerCrop()
                    .into(ivImg)
                helper.setVisible(R.id.iv_play, false)
            }
            helper.setVisible(R.id.cl_cb, false)
            helper.setVisible(R.id.tv_time, false)
        }

    }

    private fun secToTime(data: String): String? {
        val time = data.toInt() / 1000
        var timeStr: String?
        var hour: Int
        var minute: Int
        var second: Int
        if (time <= 0) return "00:00" else {
            minute = time / 60
            if (minute < 60) {
                second = time % 60
                timeStr = unitFormat(minute) + ":" + unitFormat(second)
            } else {
                hour = minute / 60
                if (hour > 99) return "99:59:59"
                minute %= 60
                second = time - hour * 3600 - minute * 60
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second)
            }
        }
        return timeStr
    }

    private fun unitFormat(i: Int): String {
        return if (i in 0..9) "0$i" else "" + i
    }
}
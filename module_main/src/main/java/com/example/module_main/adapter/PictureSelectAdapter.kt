package com.example.module_main.adapter

import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.data.MediaInfoBean
import com.example.module_main.R

/**
 * @Author Administrator
 * @ClassName PictureSelectAdapter
 * @Description
 * @Date 2021/8/6 14:39
 */
class PictureSelectAdapter(layoutResId: Int, data: MutableList<MediaInfoBean>) :
    BaseQuickAdapter<MediaInfoBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: MediaInfoBean) {
        val ivImg = helper.getView<ImageView>(R.id.iv_image)
        val cbImage = helper.getView<CheckBox>(R.id.cb_image)
        if (item.fileType == 1) {
            Glide.with(ivImg)
                .setDefaultRequestOptions(RequestOptions().frame(1000).centerCrop())
                .load(item.filePath)
                .into(ivImg)

            helper.setText(R.id.tv_time, getVideoTime(item.fileDurationTime.toString()))
            helper.setVisible(R.id.tv_time, true)
        } else {
            Glide.with(ivImg)
                .load(item.filePath)
                .into(ivImg)
            helper.setVisible(R.id.tv_time, false)
        }
//        if (item.isSelect){
//            cbImage.setBackgroundResource(R.drawable.shape_select_picture)
//        }else{
//            cbImage.setBackgroundResource(R.drawable.shape_no_select_picture)
//        }
        cbImage.isChecked = item.isSelect
        helper.addOnClickListener(R.id.cb_image)
    }

    private fun getVideoTime(time: String): String {
        val toInt = time.toInt()
        var s = "00:00"
        val i = toInt / 1000
        s = if (i < 60) {
            if (i < 10) {
                "00:0${i}"
            } else {
                "00:${i}"
            }
        } else {
            val i1 = i / 60
            val i2 = i % 60
            if (i1 < 10) {
                if (i2 < 10) {
                    "0$i1:0$i2"
                } else {
                    "0$i1:$i2"
                }
//            } else if (i1 > 60) { //分 》 60
//                val i3 = i1 / 60 //时
//                val i4 = i1 % 60 // 分和秒
//                if (i4 < 10) {
//                    "$i3:00:0$i4"
//                } else {
//                    var i5 = i4 / 60
//                    var i6 = i4 % 60
//                    if (i5 < 10) {
//                        "$i3:0$i5:$i6"
//                    } else {
//                        "$i3:$i5:$i6"
//                    }
//                }
//            }
            } else {
                if (i2 < 10) {
                    "$i1:0$i2"
                } else {
                    "$i1:$i2"
                }
            }
        }
        Log.i(TAG, "getVideoTime: $s")
        return s
    }
}
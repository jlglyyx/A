package com.example.lib_common.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lib_common.data.BannerBean
import com.example.lib_common.util.showShort
import com.google.android.material.imageview.ShapeableImageView
import com.youth.banner.adapter.BannerAdapter


/**
 * @ClassName BannerAdapter
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/3 14:19
 */
class MBannerAdapter(mData: MutableList<BannerBean>) : BannerAdapter<BannerBean, MBannerAdapter.BannerViewHolder>(mData) {


    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var shapeAbleImageView: ShapeableImageView = itemView as ShapeableImageView

    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {

        val shapeAbleImageView = ShapeableImageView(parent.context)
        shapeAbleImageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        shapeAbleImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(shapeAbleImageView)

    }

    override fun onBindView(holder: BannerViewHolder, data: BannerBean, position: Int, size: Int) {
        holder.shapeAbleImageView.setOnClickListener {
            showShort("$position ${data.url}")
        }
        Glide.with(holder.shapeAbleImageView).load(data.url).into(holder.shapeAbleImageView)
    }


}
package com.example.module_picture.ui.fragment

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_common.base.ui.fragment.BaseLazyFragment
import com.example.lib_common.bus.event.UIChangeLiveData
import com.example.lib_common.constant.AppConstant
import com.example.lib_common.util.buildARouter
import com.example.module_picture.R
import com.example.module_picture.helper.getPictureComponent
import com.example.module_picture.model.ImageDataItem
import com.example.module_picture.viewmodel.PictureViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.fra_item_picture.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT)
class PictureItemFragment : BaseLazyFragment(), OnRefreshLoadMoreListener {

    @Inject
    lateinit var pictureModule: PictureViewModel

    private var queryType: String? = null

    private var pageNum = 1


    lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.fra_item_picture
    }

    override fun initData() {
        queryType = arguments?.getString(AppConstant.Constant.TYPE)
        smartRefreshLayout.autoRefresh()
    }

    override fun initView() {
        initRecyclerView()
        initSmartRefreshLayout()

    }

    override fun initUIChangeLiveData(): UIChangeLiveData? {
        return pictureModule.uC
    }

    override fun initViewModel() {
        getPictureComponent(this).inject(this)

    }

    private fun initSmartRefreshLayout() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(this)
        finishRefreshLoadMore(smartRefreshLayout)
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = MAdapter(R.layout.item_picture_image, mutableListOf()).also {
            it.setOnItemClickListener { adapter, view, position ->
                val imageData = adapter.data[position] as ImageDataItem
                buildARouter(AppConstant.RoutePath.PICTURE_ITEM_ACTIVITY)
                    .withString(AppConstant.Constant.ID, imageData.id)
                    .navigation()
            }
        }
        recyclerView.adapter = mAdapter
        pictureModule.mImageData.observe(this, Observer {

            when {
                smartRefreshLayout.isRefreshing -> {
                    smartRefreshLayout.finishRefresh()
                    mAdapter.replaceData(it.list)
                }
                smartRefreshLayout.isLoading -> {
                    smartRefreshLayout.finishLoadMore()
                    if (pageNum != 1 && it.list.isEmpty()) {
                        smartRefreshLayout.setNoMoreData(true)
                    } else {
                        smartRefreshLayout.setNoMoreData(false)
                        mAdapter.addData(it.list)
                    }
                }
                else -> {
                    mAdapter.replaceData(it.list)
                }
            }
        })


    }

    inner class MAdapter(layoutResId: Int, list: MutableList<ImageDataItem>) :
        BaseQuickAdapter<ImageDataItem, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: ImageDataItem) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            val avi = helper.getView<AVLoadingIndicatorView>(R.id.avi)
            helper.setTag(R.id.siv_img, item.id)
            Glide.with(sivImg)
                .load(item.imageUrl)
                .into(object : CustomViewTarget<ShapeableImageView, Drawable>(sivImg) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        avi.visibility = View.VISIBLE
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        sivImg.setImageDrawable(null)
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        avi.visibility = View.GONE
                        if (sivImg.tag == item.id) {
                            sivImg.setImageDrawable(resource)
                        }
                    }

                })
        }
    }


    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum++
        pictureModule.getImageInfo(queryType ?: "", pageNum)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum = 1
        pictureModule.getImageInfo(queryType ?: "", pageNum)
    }
}
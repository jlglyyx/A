package com.yang.module_picture.ui.fragment

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.lib_common.adapter.MBannerAdapter
import com.yang.lib_common.base.ui.fragment.BaseLazyFragment
import com.yang.lib_common.bus.event.UIChangeLiveData
import com.yang.lib_common.constant.AppConstant
import com.yang.lib_common.data.BannerBean
import com.yang.lib_common.util.buildARouter
import com.yang.module_picture.R
import com.yang.module_picture.helper.getPictureComponent
import com.yang.module_picture.model.ImageDataItem
import com.yang.module_picture.viewmodel.PictureViewModel
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fra_item_picture.*
import javax.inject.Inject

@Route(path = AppConstant.RoutePath.PICTURE_ITEM_FRAGMENT)
class PictureItemFragment : BaseLazyFragment(), OnRefreshLoadMoreListener {

    @Inject
    lateinit var pictureModule: PictureViewModel

    private var queryType: String? = null

    private var pageNum = 1


    private lateinit var mAdapter: MAdapter

    override fun getLayout(): Int {
        return R.layout.fra_item_picture
    }

    override fun initData() {
        queryType = arguments?.getString(AppConstant.Constant.TYPE)
        val mutableListOf = mutableListOf<ImageDataItem>()
        for (i in 0..9){
            mutableListOf.add(ImageDataItem(null,null,"","","https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg","","","",""))
        }
        mAdapter.replaceData(mutableListOf)
    }

    override fun initView() {
        initRecyclerView()
        initBanner()
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
        smartRefreshLayout.autoRefresh()
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

    private fun initBanner() {
//        banner.setPageTransformer(AlphaPageTransformer())
        //       banner.addPageTransformer(DepthPageTransformer())
//        banner.addPageTransformer(RotateDownPageTransformer())
//        banner.addPageTransformer(RotateUpPageTransformer())
//        banner.addPageTransformer(RotateYTransformer())
//        banner.addPageTransformer(ScaleInTransformer())
        //       banner.addPageTransformer(ZoomOutPageTransformer())
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(MBannerAdapter(mutableListOf<BannerBean>().apply {
                add(BannerBean("https://scpic.chinaz.net/Files/pic/pic9/202107/bpic23678_s.jpg"))
                add(BannerBean("https://scpic1.chinaz.net/Files/pic/pic9/202107/apic33909_s.jpg"))
                add(BannerBean("https://scpic2.chinaz.net/Files/pic/pic9/202107/bpic23656_s.jpg"))
                add(BannerBean("https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4186_s.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32184.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32185.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202106/apic33150.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32187.jpg"))
                add(BannerBean("https://scpic.chinaz.net/files/pic/pic9/202104/apic32186.jpg"))
                add(BannerBean("https://scpic3.chinaz.net/Files/pic/pic9/202107/hpic4166_s.jpg"))
            }))
            .indicator = CircleIndicator(requireContext())
    }

    inner class MAdapter(layoutResId: Int, list: MutableList<ImageDataItem>) :
        BaseQuickAdapter<ImageDataItem, BaseViewHolder>(layoutResId, list) {
        override fun convert(helper: BaseViewHolder, item: ImageDataItem) {
            val sivImg = helper.getView<ShapeableImageView>(R.id.siv_img)
            helper.setTag(R.id.siv_img, item.id)
                .setText(R.id.tv_title,item.imageTitle)
                .setText(R.id.tv_desc,item.imageDesc)
//                .setGone(R.id.tv_title,!TextUtils.isEmpty(item.imageTitle))
//                .setGone(R.id.tv_desc,!TextUtils.isEmpty(item.imageDesc))
            Glide.with(sivImg)
                .load(item.imageUrl)
                .error(R.drawable.iv_image_error)
                .placeholder(R.drawable.iv_image_placeholder)
                .into(sivImg)
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